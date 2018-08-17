package ${myClass.packageUrl}

import com.zrj.pay.core.service.BaseTransactionService;
import com.zrj.pay.tms.po.${myClass.baseName}Entity;
import com.zrj.pay.tms.dao.${myClass.baseName}DAO;



@Component("${myClass.componentName}")
public interface ${myClass.className} extends BaseTransactionService<${myClass.baseName}Entity>{
    @Autowired
    private ${myClass.baseName}Dao baseDao;

    @Autowired
    public void setBaseDao(${myClass.baseName}Dao baseDao) {
        super.setBaseDao(baseDao);
    }
}