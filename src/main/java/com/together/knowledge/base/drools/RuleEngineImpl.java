package com.together.knowledge.base.drools;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

import com.together.knowledge.base.util.AnnoManageUtil;
import org.drools.RuleBase;
import org.drools.StatefulSession;
import org.drools.compiler.PackageBuilder;
import org.drools.spi.Activation;

/**
 *
 */
public class RuleEngineImpl implements RuleEngine {

    private static Map<String,RuleBase> cachRuleBase = new HashMap<>();

    public void initEngine() throws Exception {
        List<Class<?>> list = AnnoManageUtil.getPackageController("com.together.knowledge.base.drools",DroolsEngineEntity.class);
        for (Class entity: list) {
            DroolsEngineEntity droolsEngineEntity = (DroolsEngineEntity) entity.getAnnotation(DroolsEngineEntity.class);
            String[] fileNames = droolsEngineEntity.fileNames();
            String name = droolsEngineEntity.name();
            System.setProperty("drools.dateformat", "yyyy-MM-dd HH:mm:ss");
            RuleBase ruleBase = RuleBaseFacatory.getRuleBase();
            PackageBuilder backageBuilder = getPackageBuilderFromDrlFile(fileNames);
            ruleBase.addPackages(backageBuilder.getPackages());
            cachRuleBase.put(name,ruleBase);
        }
    }

    public void refreshEnginRule() throws Exception {
        Set<Map.Entry<String, RuleBase>> entrySet = cachRuleBase.entrySet();
        Iterator<Map.Entry<String, RuleBase>> it = entrySet.iterator();
        while(it.hasNext()){
            Map.Entry<String, RuleBase> entry = it.next();
            RuleBase ruleBase = entry.getValue();
            ruleBase = RuleBaseFacatory.getRuleBase();
            org.drools.rule.Package[] packages = ruleBase.getPackages();
            for(org.drools.rule.Package pg : packages) {
                ruleBase.removePackage(pg.getName());
            }
            initEngine();
        }
    }

    public void executeRuleEngine(final Object obj) throws Exception {
        DroolsEngineEntity droolsEngineEntity = obj.getClass().getAnnotation(DroolsEngineEntity.class);
        if(droolsEngineEntity == null){
            throw new Exception("实体不符合定义规范!");
        }
        String name = droolsEngineEntity.name();
        RuleBase ruleBase = cachRuleBase.get(name);
        if(null == ruleBase.getPackages() || 0 == ruleBase.getPackages().length) {
            return;
        }
        StatefulSession statefulSession = ruleBase.newStatefulSession();
        statefulSession.insert(obj);
        statefulSession.fireAllRules(new org.drools.spi.AgendaFilter() {
            public boolean accept(Activation activation) {
                return !activation.getRule().getName().contains("_test");
            }
        });
        statefulSession.dispose();
    }

    @Override
    public void testRuleEngine(Object obj) throws Exception {
        DroolsEngineEntity droolsEngineEntity = obj.getClass().getAnnotation(DroolsEngineEntity.class);
        String[] fileNames = droolsEngineEntity.fileNames();
        getPackageBuilderFromDrlFile(fileNames);
    }

    private PackageBuilder getPackageBuilderFromDrlFile(String[] filePaths ) throws Exception {
        PackageBuilder backageBuilder = new PackageBuilder();
        for(String filePath : filePaths) {
            Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(filePath);
            if(dirs.hasMoreElements()){
                URL url = dirs.nextElement();
                String drlFilePath = URLDecoder.decode(url.getFile(), "UTF-8");
                Reader r = new FileReader(new File(drlFilePath));
                backageBuilder.addPackageFromDrl(r);
            }else{
                throw new Exception(filePath+":未找到相应文件");
            }
        }
        if(backageBuilder.hasErrors()) {
            throw new Exception(backageBuilder.getErrors().toString());
        }
        return backageBuilder;
    }

}
