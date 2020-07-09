package com.dantefung.thinkinginspringbootsamples.expression;

import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.Operation;
import org.springframework.expression.OperatorOverloader;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 条件表达式解析器(springEL)
 */

/**
 * springel表达式例子：
 * <code>String expression = "grade==1 && type=='1' && days>=1";
 *         ApplyConditionObject.ApplyCondition condition =ApplyConditionObject.ApplyCondition.builder().days(2).grade(1).type("1").build();
 *         boolean result = parser.caculate(expression,new ApplyConditionObject(condition));
 *         Assert.isTrue(result,"表达式执行结果为true");
 *         </code>
 */
@Component
@Slf4j
public class ConditionParser implements OperatorOverloader{

    private ExpressionParser parser ;

    @PostConstruct
    public void init(){
        parser = new SpelExpressionParser();
    }

    /**
     *
     * @param expressionString springel表达式字符串
     * @param conditionObject 条件对象
     * @return
     */
    public boolean caculate(String expressionString,ConditionObject<?> conditionObject)  {
        if(conditionObject==null || conditionObject.getObject()==null){
            throw new NullPointerException("计算条件参数不能为空");
        }
        log.info("开始解析表达式 {}",expressionString);
        expressionString= expressionString.replaceAll("in","^");
        log.info("转义表达式 {}",expressionString);
        StandardEvaluationContext context = new StandardEvaluationContext(conditionObject.getObject());
        context.setOperatorOverloader(this);
        return (Boolean)parser.parseExpression(expressionString).getValue(context);

    }

    @Override
    public boolean overridesOperation(Operation operation, Object leftOperand, Object rightOperand) {
        return operation.equals(Operation.POWER);
    }

    @Override
    public Object operate(Operation operation, Object leftOperand, Object rightOperand) {
        if(rightOperand instanceof List){
            List list = (List) rightOperand;
            if(list.contains(leftOperand)){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
