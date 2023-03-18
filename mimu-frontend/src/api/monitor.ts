/*
 * @Author: mimu
 * @Date: 2022-04-19 15:40:58
 * @LastEditTime: 2022-05-07 14:13:35
 */

import { GET, POST, PUT, DELETE } from "@/utils/request";

/**
 * @description: 列表
 * @param {* Object } data
 * @return {* void }
 */
export function list(params: any) {
    return GET({ url: "/list", params: params })
}
// export const list = () => request.get("/list");
// export async function list(data:String) {
//     return request({
//         url: "/list",
//         method: "get",
//         data,
//     });
// }

/**
 * @description: 增加
 * @param {* Object } data
 * @return {* void }
 */
// export const add = (data: IloginParam): dataAPI<ILoginData> =>
//     request.post("/add", data);
// export async function add(data:String) {
//     return request({
//         url: "/add",
//         method: "post",
//         data,
//     });
// }

/**
 * @description: 编辑
 * @param {* Object } data
 * @return {* void }
 */
export function edit(params: any) {
    return POST({ url: "/edit", params: params })
}
// export async function edit(data:String) {
//     return request({
//         url: "/edit",
//         method: "post",
//         data,
//     });
// }

/**
 * @description: 启用
 * @param {* Object } data
 * @return {* void }
 */
export function enable(params: any) {
    return POST({ url: "/enable", params: params })
}

/**
 * @description: 停用
 * @param {* Object } data
 * @return {* void }
 */
export function disable(params: any) {
    return POST({ url: "/disable", params: params })
}

/**
 * @description: 删除
 * @param {* Object } data
 * @return {* void }
 */
export function deleteMonitor(params: any) {
    return POST({ url: "/delete", params: params })
}

/*
// post请求 ，没参数
export const LogoutAPI = () => instance.post("/admin/logout");

// post请求，有参数
export const loginAPI = (data: IloginParam): dataAPI<ILoginData> =>
    instance.post("/admin/login", data);

// post请求 ，没参数，但要路径传参
export const StatusAPI = (data:IupdateNavStatus): dataAPI<number> =>
    instance.post(`/productCategory?ids=${data.id}&navStatus=${data.navStatus}`);


//  get请求，没参数，
export const FlashSessionListApi = (): dataAPI<{}[]> =>
    instance.get("/flashSession/list");

// get请求，有参数，路径也要传参
export const ProductCategoryApi = (params: IUseTableParam): dataAPI<any> =>
    instance.get(`/productCategory/list/${params.parentId}`, { params });

// get请求，有参数，
export const AdminListAPI = (
    params: IAdminListParam
): dataAPI<AdminListDate> => instance.get("/admin/list", { params });

 */