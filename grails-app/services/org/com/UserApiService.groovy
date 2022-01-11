package org.com

import grails.converters.JSON
import grails.transaction.Transactional
import groovy.json.JsonOutput
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

@Transactional
class UserApiService {
    private final String url = "http://localhost:8083"
    private final String url2 = "http://localhost:8084"

    def list() {
        List list = UserApi.list().collect { [userName: it.userName, api: it.api, rateLimit: it.rateLimit] }
        List<String> data = list.collect { it.userName + "-" + it.api }

        String path = "/getAllUserApiLimit"
        Map res = postData(url, path, [data: JsonOutput.toJson(data)])
        Map resultMap = JSON.parse(res.result as String) as Map
        list.each { Map currMap ->
            String apiKey = currMap.userName + "-" + currMap.api
            currMap.put("message", resultMap."${apiKey}")
        }
        list
    }

    def update(String userName, String apiName, Integer rateLimit) {
        UserApi userApi = UserApi.findByUserNameAndApi(userName, apiName)
        if (userApi && rateLimit > 0) {
            userApi.rateLimit = rateLimit
            Map res = postData(url, "/updateBucket", [data: userApi.userName + "-" + userApi.api + "," + rateLimit])
            if (res.status == 500) {
                return "Couldn't update user api"
            }
            userApi.save()
            "Updated user api"
        } else if (userName != null && !userName.isEmpty() && userName != null && !userName.isEmpty() && rateLimit != null && rateLimit > 0) {
            UserApi newUserApi = new UserApi(userName: userName, api: apiName, rateLimit: rateLimit)
            Map res = postData(url, "createBucket", [data: userName + "-" + apiName + "," + rateLimit])
            if (res.status == 500) {
                return "Couldn't create new user api"
            }
            newUserApi.save()
            "New user api created"
        } else {
            "Please enter valid values"
        }
    }

    def delete(String userName, String apiName) {
        UserApi userApi = UserApi.findByUserNameAndApi(userName, apiName)
        if (userApi) {
            Map res = postData(url, "/deleteBucket", [data: userApi.userName + "-" + userApi.api])
            if (res.status != 500) {
                userApi.delete()
                "Deleted user api"
            } else {
                "Couldn't delete due to some error."
            }
        }
    }

    Map postData(String baseUrl, String path, def data, method = Method.POST) {
        Map ret = [:]
        HTTPBuilder http = new HTTPBuilder(baseUrl)

        try {
            http.request(method, ContentType.URLENC) {
                uri.path = path
                body = data
                response.success = { resp, Map reader ->
                    ret = [status: 200, result: reader.keySet()[0]]
                }
                response.failure = { resp, reader ->
                    ret = [status: 500]
                }
            }
        } catch (ConnectException ct) {
            log.error(ct.getMessage())
            if (baseUrl == "http://localhost:8083") {
                log.info("trying to connect to a different server.")
                ret = postData(url2, path, data)
            } else {
                ret = [status: 500, error: ct.getMessage()]
            }
        } catch (Throwable t) {
            log.error(t.getMessage(), t)
            ret = [status: 500, error: t.getMessage()]
        }
        return ret
    }
}
