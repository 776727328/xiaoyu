package com.docker.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Yuanqiang.Zhang
 * @since 2023/5/15
 */
public class GeneratorTool {

    /** 数据源配置 */
    private static final String IP = "47.116.113.174";
    private static final int PORT = 3306;
    private static final String DATABASE = "docker";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";
    /** 基础配置 */
    private static final String AUTHOR = "csy";
    private static final String BASE_PACKAGE = "com.docker";
    private static final String[] TABLE_NAMES = {"user", };
    private static final String[] IGNORE_PREFIXES = {"z_"};
    /** 指定生成路径（如果配置了该路径，则所有文件将统一生成到该路径下，如果不配置，则将按照标准生成到项目中） */
    private static final String PATH = "D:\\code2023\\temp";

    public static void main(String[] args) throws Exception {
        Connection conn = getConnection();
        for (String tableName : TABLE_NAMES) {
            createTableFiles(conn, tableName);
        }
    }

    private static String getWithoutPrefixTableName(String tableName) {
        for (String s : IGNORE_PREFIXES) {
            if (tableName.startsWith(s)) {
                return tableName.substring(s.length());
            }
        }
        return tableName;
    }

    public static void createTableFiles(Connection conn, String tableName) {
        List<String[]> fields = getTableFields(conn, tableName);
        String withoutPrefixTableName = getWithoutPrefixTableName(tableName);
        String className = getClassName(withoutPrefixTableName);
        createEntity(fields, tableName, className, withoutPrefixTableName);
        createMapper(withoutPrefixTableName, className);
        createMapperXml(withoutPrefixTableName, className);
        createService(withoutPrefixTableName, className);
        createServiceImpl(withoutPrefixTableName, className);
        createController(withoutPrefixTableName, className);
    }

    public static void createController(String tableName, String className) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("package %s.controller;\n\n", BASE_PACKAGE));
        sb.append(String.format("import %s.service.%sService;\n", BASE_PACKAGE, className));
        sb.append("import lombok.extern.slf4j.Slf4j;\n");
        sb.append("import org.springframework.web.bind.annotation.RequestMapping;\n");
        sb.append("import org.springframework.web.bind.annotation.RestController;\n");
        sb.append("import org.springframework.beans.factory.annotation.Autowired;\n\n");
        sb.append("/**\n");
        sb.append(String.format(" * @author %s\n", AUTHOR));
        sb.append(String.format(" * @since %s\n", formatDateTime(new Date())));
        sb.append(" */\n");
        sb.append("@RestController\n");
        sb.append("@Slf4j\n");
        String path = String.valueOf(className.toCharArray()[0]).toLowerCase() + className.substring(1);
        sb.append(String.format("@RequestMapping(\"/%s\")\n", path));
        sb.append(String.format("public class %sController {\n\n", className));
        sb.append("    @Autowired\n");
        sb.append(String.format("    private %sService service;\n\n", className));
        sb.append("}\n");
        String fileName = className + "Controller.java";
        String filePath;
        if (Objects.nonNull(PATH) && !PATH.trim().isEmpty()) {
            filePath = PATH+ "\\" + fileName;
        } else {
            filePath = getBasePath() + "/controller/" + fileName;
        }
        File file = new File(filePath);
        if (file.exists()) {
            System.out.println(tableName + " 的 Controller 类已经存在，不再生成。");
        } else {
            writeFileContent(file, sb.toString());
            System.out.println(tableName + " 的 Controller 类生成完毕。");
        }
    }

    public static void createServiceImpl(String tableName, String className) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("package %s.service.impl;\n\n", BASE_PACKAGE));
        sb.append("import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;\n");
        sb.append(String.format("import %s.entity.%s;\n", BASE_PACKAGE, className));
        sb.append(String.format("import %s.mapper.%sMapper;\n", BASE_PACKAGE, className));
        sb.append(String.format("import %s.service.%sService;\n", BASE_PACKAGE, className));
        sb.append("import lombok.extern.slf4j.Slf4j;\n");
        sb.append("import org.springframework.beans.factory.annotation.Autowired;\n");
        sb.append("import org.springframework.stereotype.Service;\n\n");
        sb.append("/**\n");
        sb.append(String.format(" * @author %s\n", AUTHOR));
        sb.append(String.format(" * @since %s\n", formatDateTime(new Date())));
        sb.append(" */\n");
        sb.append("@Slf4j\n");
        sb.append("@Service\n");
        sb.append(String.format("public class %sServiceImpl extends ServiceImpl<%sMapper, %s> implements %sService { \n\n", className, className, className, className));
        sb.append("    @Autowired\n");
        sb.append(String.format("    private %sMapper mapper;\n\n", className));
        sb.append("}\n");
        String fileName = className + "ServiceImpl.java";
        String filePath;
        if (Objects.nonNull(PATH) && !PATH.trim().isEmpty()) {
            filePath = PATH+ "\\" + fileName;
        } else {
            filePath = getBasePath() + "/service/impl/" + fileName;
        }
        File file = new File(filePath);
        if (file.exists()) {
            System.out.println(tableName + " 的 ServiceImpl 已经存在，不再生成。");
        } else {
            writeFileContent(file, sb.toString());
            System.out.println(tableName + " 的 ServiceImpl 生成完毕。");
        }
    }

    public static void createService(String tableName, String className) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("package %s.service;\n\n", BASE_PACKAGE));
        sb.append("import com.baomidou.mybatisplus.extension.service.IService;\n");
        sb.append(String.format("import %s.entity.%s;\n\n", BASE_PACKAGE, className));
        sb.append("/**\n");
        sb.append(String.format(" * @author %s\n", AUTHOR));
        sb.append(String.format(" * @since %s\n", formatDateTime(new Date())));
        sb.append(" */\n");
        sb.append(String.format("public interface %sService extends IService<%s> {\n\n", className, className));
        sb.append("}\n");
        String fileName = className + "Service.java";
        String filePath;
        if (Objects.nonNull(PATH) && !PATH.trim().isEmpty()) {
            filePath = PATH+ "\\" + fileName;
        } else {
            filePath = getBasePath() + "/service/" + fileName;
        }
        File file = new File(filePath);
        if (file.exists()) {
            System.out.println(tableName + " 的 Service 类已经存在，不再生成。");
        } else {
            writeFileContent(file, sb.toString());
            System.out.println(tableName + " 的 Service 类生成完毕。");
        }
    }

    public static void createMapperXml(String tableName, String className) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
        sb.append(String.format("<mapper namespace=\"%s.mapper.%sMapper\">\n\n", BASE_PACKAGE, className));
        sb.append("</mapper>\n");
        String fileName = className + "Mapper.xml";
        String filePath = "src/main/resources/mapper/" + fileName;
        File file = new File(filePath);
        if (file.exists()) {
            System.out.println(tableName + " 的 Mapper.xml 已经存在，不再生成。");
        } else {
            writeFileContent(file, sb.toString());
            System.out.println(tableName + " 的 Mapper.xml 生成完毕。");
        }
    }

    public static void createMapper(String tableName, String className) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("package %s.mapper;\n\n", BASE_PACKAGE));
        sb.append(String.format("import %s.entity.%s;\n", BASE_PACKAGE, className));
        sb.append("import com.baomidou.mybatisplus.core.mapper.BaseMapper;\n\n");
        sb.append("/**\n");
        sb.append(String.format(" * @author %s\n", AUTHOR));
        sb.append(String.format(" * @since %s\n", formatDateTime(new Date())));
        sb.append(" */\n");
        sb.append(String.format("public interface %sMapper extends BaseMapper<%s> {\n\n", className, className));
        sb.append("}\n");
        String fileName = className + "Mapper.java";
        String filePath;
        if (Objects.nonNull(PATH) && !PATH.trim().isEmpty()) {
            filePath = PATH+ "\\" + fileName;
        } else {
            filePath = getBasePath()  + "/mapper/" + fileName;
        }
        File file = new File(filePath);
        if (file.exists()) {
            System.out.println(tableName + " 的 Mapper 类已经存在，不再生成。");
        } else {
            writeFileContent(file, sb.toString());
            System.out.println(tableName + " 的 Mapper 类生成完毕。");
        }
    }

    public static void createEntity(List<String[]> fields, String tableName, String className, String withoutPrefixTableName) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("package %s.entity;\n\n", BASE_PACKAGE));
        sb.append("import com.baomidou.mybatisplus.annotation.*;\n");
        sb.append("import lombok.Data;\n\n");
        // 判断是否有 timestamp 类型
        if (hasDate(fields)) {
            sb.append("import java.util.Date;\n\n");
        }
        sb.append("/**\n");
        sb.append(" * @author " + AUTHOR + "\n");
        sb.append(" * @since ").append(formatDateTime(new Date())).append("\n");
        sb.append(" */\n");
        sb.append("@Data\n");
        sb.append(String.format("@TableName(\"%s\")\n", tableName));
        sb.append(String.format("public class %s {\n\n", className));
        for (String[] attrs : fields) {
            String columnName = attrs[0];
            String dataType = attrs[1];
            String columnComment = attrs[2];
            String columnKey = attrs[3];
            if ("PRI".equals(columnKey)) {
                sb.append("    /** 主键 */\n");
                sb.append(String.format("    @TableId(value = \"%s\", type = IdType.AUTO)\n", columnName));
            } else {
                if (Objects.nonNull(columnComment) && !columnComment.isEmpty()) {
                    sb.append(String.format("    /** %s */\n", columnComment));
                }
                sb.append(String.format("    @TableField(value = \"%s\")\n", columnName));
            }
            String fieldName = getFieldName(columnName);
            if (dataType.endsWith("int")) {
                if ("bigint".equals(dataType)) {
                    sb.append(String.format("    private Long %s;\n", fieldName));
                } else {
                    sb.append(String.format("    private Integer %s;\n", fieldName));
                }
            } else if (dataType.endsWith("char") || dataType.endsWith("text")) {
                sb.append(String.format("    private String %s;\n", fieldName));
            } else if ("timestamp".equals(dataType) || "datetime".equals(dataType) || "date".equals(dataType)) {
                sb.append(String.format("    private Date %s;\n", fieldName));
            } else if ("double".equals(dataType)) {
                sb.append(String.format("    private Double %s;\n", fieldName));
            }  else if ("decimal".equals(dataType)) {
                sb.append(String.format("    private BigDecimal %s;\n", fieldName));
            } else {
                System.err.println("发现没有判断到的数据类型：" + dataType);
            }
            sb.append("\n");
        }
        sb.append("\n}");
        String fileName = className + ".java";
        String filePath;
        if (Objects.nonNull(PATH) && !PATH.trim().isEmpty()) {
            filePath = PATH+ "\\" + fileName;
        } else {
            filePath = getBasePath() + "/entity/" + fileName;
        }
        File file = new File(filePath);
        if (file.exists()) {
            System.out.println(withoutPrefixTableName + " 的 Entity 类已经存在，不再生成。");
        } else {
            writeFileContent(file, sb.toString());
            System.out.println(withoutPrefixTableName + " 的 Entity 类生成完毕。");
        }
    }

    private static String getBasePath() {
        return "src/main/java/" + BASE_PACKAGE.replace(".", "/");
    }

    private static String getFieldName(String columnName) {
        if (!columnName.contains("_")) {
            StringBuilder sb = new StringBuilder();
            char[] chars = columnName.toCharArray();
            sb.append(String.valueOf(chars[0]).toLowerCase());
            if (chars.length <= 1) {
                return sb.toString();
            } else {
                for (int i = 1; i < chars.length; i++) {
                    sb.append(chars[i]);
                }
            }
            return sb.toString();
        }
        String s = getClassName(columnName);
        return String.valueOf(s.toCharArray()[0]).toLowerCase() + s.substring(1);
    }

    private static boolean hasDate(List<String[]> fields) {
        if (Objects.isNull(fields) || fields.isEmpty()) {
            return false;
        }
        for (String[] attrs : fields) {
            String dataType = attrs[1];
            if ("timestamp".equals(dataType) || "datetime".equals(dataType)) {
                return true;
            }
        }
        return false;
    }

    private static List<String[]> getTableFields(Connection conn, String tableName) {
        String sql = "SELECT * FROM information_schema.columns WHERE table_schema = '" + DATABASE + "' AND table_name = '" + tableName + "'";
        List<String[]> fields = new ArrayList<>();
        try {
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                String dataType = rs.getString("DATA_TYPE");
                String columnComment = rs.getString("COLUMN_COMMENT");
                String columnKey = rs.getString("COLUMN_KEY");
                String[] attrs = new String[4];
                attrs[0] = columnName;
                attrs[1] = dataType;
                attrs[2] = columnComment;
                attrs[3] = columnKey;
                fields.add(attrs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fields;
    }


    /**
     * 获取类名
     *
     * @param tableName 表名
     * @return 类名
     */
    private static String getClassName(String tableName) {
        char[] chars = tableName.toCharArray();
        boolean flag = true;
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if (c == '_') {
                flag = true;
                continue;
            }
            if (flag) {
                sb.append(String.valueOf(c).toUpperCase());
            } else {
                sb.append(String.valueOf(c).toLowerCase());
            }
            flag = false;
        }
        return sb.toString();
    }

    /**
     * 获取数据库链接
     *
     * @return Connection
     */
    private static Connection getConnection() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = String.format("jdbc:mysql://%s:%s/%s", IP, PORT, DATABASE);
        return DriverManager.getConnection(url, USERNAME, PASSWORD);
    }


    /**
     * 将内容写入到文件中
     *
     * @param file    文件
     * @param content 内容
     */
    public static void writeFileContent(File file, String content) {
        String parentPath = file.getParent();
        if (Objects.nonNull(parentPath) && !parentPath.isEmpty()) {
            File parent = new File(parentPath);
            if (!parent.exists()) {
                parent.mkdirs();
                System.out.println("创建路径" + parent.getPath());
            }
        }
        if (!file.exists()) {
            try {
                System.out.println("创建文件：" + file.getPath());
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String formatDateTime(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(date);
    }
}
