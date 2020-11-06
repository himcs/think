package io.himcs.sdk.util;

import com.intellij.database.model.DasNamed;
import com.intellij.database.model.DasTable;
import com.intellij.database.psi.DbDataSource;
import com.intellij.database.util.DasUtil;
import com.intellij.database.util.DbUtil;
import com.intellij.openapi.project.Project;
import com.intellij.util.containers.JBIterable;

import java.util.HashSet;
import java.util.Set;

public class MDbUtil {
    public final static Set<String> cacheTables = new HashSet<>();

    //获取所有表
    public static JBIterable<? extends DasTable> getTables(Project project) {
        JBIterable<DbDataSource> dataSources = DbUtil.getDataSources(project);
        if (dataSources.size() < 1) {
            return null;
        } else {
            DbDataSource work = null;
            for (DbDataSource db : dataSources) {
                if (db.getName().contains("work")) {
                    work = db;
                    break;
                }
            }
            JBIterable<? extends DasTable> iterable;
            if (work != null) {
                iterable = DasUtil.getTables(work);
            } else {
                iterable = DasUtil.getTables(dataSources.get(0));
            }
            return iterable;
        }
    }
}
