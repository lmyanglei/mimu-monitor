/*
 * @Author: gaomiaomiao
 * @Date: 2022-04-19 13:43:13
 * @LastEditTime: 2022-04-19 15:48:16
 */

/**
 * @description: 根据key获取本地存储value
 * @param {* String } param
 * @return {* any }
 * @author: houyueke
 */
 export function getItem(key:string) {
    if (!key) {
      return;
    }
    return window.localStorage.getItem(key);
  }

  /**
   * @description: 根据key,value设置本地存储
   * @param {* String } key
   * @param {* any } value
   * @return {* void }
   * @author: houyueke
   */
  export function setItem(key:string, value:string) {
    if (!key || !value) {
      return;
    }
    window.localStorage.setItem(key, value);
  }

  /**
   * @description: 根据key移除本地存储
   * @param {* String } key
   * @return {* void }
   * @author: houyueke
   */
  export function removeItem(key:string) {
    if (!key) {
      return;
    }
    window.localStorage.removeItem(key);
  }

  /**
   * @description: 清空本地存储
   * @param {* void }
   * @return {* void }
   * @author: houyueke
   */
  export function clearItem() {
    window.localStorage.clear();
  }

