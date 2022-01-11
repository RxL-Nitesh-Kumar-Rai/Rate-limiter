package org.com

class UserApi implements Serializable {
    transient String id
    String userName
    String api
    Integer rateLimit

    static constraints = {
        userName(nullable: false, blank: false)
        id composite: ['userName', 'api']
        api nullable: false, blank: false
        rateLimit nullable: false
    }
    static mapping = {
        table("MAPPING_TABLE")
        version false
        userName column: "USER_NAME", length: 255
        api column: "API", length: 255
        rateLimit column: "RATE_LIMIT"
    }
}
