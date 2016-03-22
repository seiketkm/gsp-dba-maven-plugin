<#-- IDENTITY型に対してALTER TABLEでPKを付与しようとするとエラーが発生するため、PKは一律CREATE TABLEで付与する -->
<#if !index.isPrimaryKey()>
<#if index.type=1>
ALTER TABLE <#if entity.schema??>${entity.schema}</#if>${entity.name}_TEST ADD CONSTRAINT ${index.name!} UNIQUE
<#else>
CREATE <#if index.type=2>UNIQUE </#if>INDEX ${index.name} ON <#if entity.schema??>${entity.schema}</#if>${entity.name}
</#if>
(
<#foreach column in index.columnList>
  ${column.name}<#if column_has_next>,</#if>
</#foreach>
);
</#if>
