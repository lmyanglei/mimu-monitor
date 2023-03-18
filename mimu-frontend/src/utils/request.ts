/*
 * @Author: mimu
 * @Date: 2022-04-19 15:41:14
 * @LastEditTime: 2022-04-22 18:04:09
 */
import axios, {Axios} from "axios";
import router from "@/router";
import {getItem,clearItem,removeItem,setItem} from "@/utils/tools";
import { ElMessage } from "element-plus";

interface requestType {
    url: string
    params?: any
}

const service = axios.create({
    baseURL: "/monitor/api",
    timeout: 10000000000,
});

//设置请求拦截器
service.interceptors.request.use(
    async (config) => {
        config.headers["content-type"] = 'application/json';
        const token = getItem("token") || "";
        config.headers["token"] = token;
        return config;
    },
    async (error) => {
        return Promise.reject(error);
    }
);
//设置响应拦截器
service.interceptors.response.use(
    async (response) => {
        return response.data;
    },
    async (error) => {
        if (error && error.response) {
            switch (error.response.status) {
                case 400:
                    error.message = error.response.data.errmsg;
                    break;
                case 401:
                    clearItem();
                    router.push({
                        path: "/login",
                    });
                    error.message = "Unauthorized，Please Sign In";
                    break;
                case 403:
                    error.message = "拒绝访问";
                    break;
                case 404:
                    error.message = `Url Error: ${error.response.config.url}`;
                    break;
                case 408:
                    error.message = "Time Out";
                    break;
                case 500:
                    error.message = "Server Error";
                    break;
                case 501:
                    error.message = "服务未实现";
                    break;
                case 502:
                    error.message = "网关错误";
                    break;
                case 503:
                    error.message = "服务不可用";
                    break;
                case 504:
                    error.message = "网关超时";
                    break;
                case 505:
                    error.message = "HTTP版本不受支持";
                    break;
                case 302:
                    error.message = error.response.data.errmsg;
                    break;
                default:
                    break;
            }
            ElMessage.error(error.message);
        }
        return Promise.reject(error);
    }
);

/**
 * @description GET
 */
const GET = (data: requestType) => {
    return service.get(data.url, data.params)
}

/**
 * @description POST
 */
const POST = (data: requestType) => {
    return service.post(data.url, data.params)
}
/**
 * @description PUT
 */
const PUT = (data: requestType) => {
    return service.put(data.url, data.params)
}

/**
 * @description DELETE
 */
const DELETE = (data: requestType) => {
    return service({
        url: data.url,
        method: 'delete',
        data: data.params,
    })
}

/**
 * @description PATCH
 */
const PATCH = (data: requestType) => {

    return new Promise((resolve, reject) => {
        service
            .put(data.url, data.params)
            .then((res: any) => {
                if (res && res.status == 200) {
                    resolve(res)
                }
            })
            .catch((error: any) => {
                reject(error)
            })
    })
}
export { GET, POST, PUT, DELETE, PATCH }
export default service;
