package com.luo;

import org.apache.commons.lang.ArrayUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HbaseMgr extends Configured implements Tool {
    private static final Logger LOGGER = LoggerFactory.getLogger(HbaseMgr.class);

    protected HbaseMgr() {
    }

    public static void main(String[] args) throws Exception {
        LOGGER.info(">>>>> enters main with params:" + ArrayUtils.toString(args));
        int exitCode = ToolRunner.run(new HbaseMgr(), args);
        LOGGER.info(">>>>> ready to exit ");
        System.exit(exitCode);
    }

    public int run(String[] aArgs) throws Exception {
        ParameterParser parser = new ParameterParser(aArgs);
        LOGGER.info("{}", parser);

        Configuration config = HBaseConfiguration.create(getConf());
        HbaseCompactor hbaseCompactor = new HbaseCompactor(parser.getMinFileCount(),
            parser.getServerConcurrency());
        hbaseCompactor.collectCompactInfo(config);
        hbaseCompactor.majorCompact(config, parser.getTimePeriod());
        return 0;
    }
}

