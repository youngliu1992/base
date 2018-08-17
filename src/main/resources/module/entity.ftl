package ${myClass.packageUrl}

public class ${myClass.className} {

<#list myClass.fieldList as field>
        //${field.fieldRemarks}
        private ${field.fieldType} ${field.fieldName};
</#list>

<#list myClass.fieldList as field>
        public ${field.fieldType} get${field.fieldNameUpperFirstLetter}() {
            return ${field.fieldName};
        }

        public void set${field.fieldNameUpperFirstLetter}(${field.fieldType} ${field.fieldName}) {
            this.${field.fieldName} = ${field.fieldName};
        }
</#list>
}