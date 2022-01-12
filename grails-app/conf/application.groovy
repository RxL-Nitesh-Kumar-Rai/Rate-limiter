

grails.mime.use.accept.header = true
grails.mime.types = [
                      html         : ['text/html', 'application/xhtml+xml'],
                      form         : 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data',
                      all          : '*/*',
                      atom         : 'application/atom+xml',
                      css          : 'text/css',
                      csv          : 'text/csv',
                      js           : 'text/javascript',
                      json         : ['application/json', 'text/json'],
                      rss          : 'application/rss+xml',
                      text         : 'text/plain',
                      hal          : ['application/hal+json', 'application/hal+xml'],
                      xml          : ['text/xml', 'application/xml'],
                      pdf          : ['application/pdf'],
                      xlsx         : ['application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'],
                      docx         : ['application/vnd.openxmlformats-officedocument.wordprocessingml.document']
]

grails.views.default.codec = "html"

grails.controllers.defaultScope = 'singleton'

// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'html' // escapes values inside ${}
                scriptlet = 'html' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'raw' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        filteringCodecForContentType.'text/html' = 'html'
    }
}

endpoints.enabled = false
endpoints.info.enabled = true

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

// configure passing transaction's read-only attribute to Hibernate session, queries and criterias
// set "singleSession = false" OSIV mode in hibernate configuration after enabling
grails.hibernate.pass.readonly = false
// configure passing read-only to OSIV session by default, requires "singleSession = false" OSIV mode
grails.hibernate.osiv.readonly = false

hibernate {
    cache {
        queries = false
        use_second_level_cache = true
        use_query_cache = true
        setProperty 'region.factory_class', 'org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory'
    }
    //This is to resolve hibernate many-to-many lazy loading issue
    enable_lazy_load_no_trans = true
    jdbc {
        use_get_generated_keys = true
    }
    format_sql = true
    use_sql_comments = true
    logSql = false
    singleSession = true
}

server.port = 9092


oracleProperties {
    jmxEnabled = true
    initialSize = 30
    maxActive = 130
    minIdle = 30
    maxIdle = 50
    maxWait = 10000
    maxAge = 10 * 60000
    timeBetweenEvictionRunsMillis = 5000
    minEvictableIdleTimeMillis = 60000
    validationQuery = "SELECT 1 FROM DUAL"
    validationQueryTimeout = 3
    validationInterval = 15000
    testOnBorrow = true
    testWhileIdle = true
    testOnReturn = false
    //https://github.com/grails/grails-core/issues/3017
    // controls for leaked connections
    abandonWhenPercentageFull = 80 // settings are active only when pool is 80% full
    removeAbandonedTimeout = 120
    removeAbandoned = true
    jdbcInterceptors = "ConnectionState;StatementCache(max=200)"
    defaultTransactionIsolation = java.sql.Connection.TRANSACTION_READ_COMMITTED
}


environments {
    development {
            dataSource {
                driverClassName = 'oracle.jdbc.OracleDriver'
                dialect = 'org.hibernate.dialect.Oracle12cDialect'
                url = 'jdbc:oracle:thin:@localhost:1521/orcl'
                username = 'rl'
                password = 'rl'
                properties = oracleProperties
            }
    }
}
