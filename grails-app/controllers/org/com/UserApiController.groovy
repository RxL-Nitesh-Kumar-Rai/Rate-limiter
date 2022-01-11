package org.com

class UserApiController {
    def userApiService

    def index() {
        List list = userApiService.list()
        render(view: "index", model: [list:list])
    }

    def update(String userName, String apiName, Integer rateLimit){
        flash.message = userApiService.update(userName, apiName, rateLimit)
        redirect(view: "index")
    }

    def delete(String userName, String apiName){
        flash.message = userApiService.delete(userName, apiName)
        redirect(view: "index")
    }

}
