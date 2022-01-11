package rate_limiter_groovy

import org.com.UserApiController

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: "userApi", action: "index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
