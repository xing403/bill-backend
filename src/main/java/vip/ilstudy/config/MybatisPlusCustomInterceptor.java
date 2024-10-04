package vip.ilstudy.config;

import com.baomidou.mybatisplus.extension.plugins.handler.MultiDataPermissionHandler;
import lombok.Data;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import vip.ilstudy.entity.LoginUserEntity;
import vip.ilstudy.utils.ServletUtils;

/**
 * mybatis plus 自定义 拦截器
 */
@Data
@Component
public class MybatisPlusCustomInterceptor implements MultiDataPermissionHandler {
    Logger log = LoggerFactory.getLogger(MybatisPlusCustomInterceptor.class);


    @Override
    public Expression getSqlSegment(Table table, Expression where, String mappedStatementId) {
        // 在此处编写自定义数据权限逻辑
        try {
            LoginUserEntity loginUser = ServletUtils.getLoginUser();
            String expression = "";
            if (loginUser != null) {
                if (!loginUser.isAdmin()) {
                    expression = setSelectByCreateBy(loginUser.getUsername());
                }
            }
            return CCJSqlParserUtil.parseCondExpression(expression);
        } catch (JSQLParserException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String setSelectByCreateBy(String username) {
        return "create_by = '" + username + "'";
    }
}
