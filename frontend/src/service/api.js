import httpClient from '@/common/http-client';

const generateStr = (length) => {
    const chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    var result = "";
    for (var i = length; i > 0; --i) result += chars[Math.floor(Math.random() * chars.length)];
    return result;
}

/**
 * httpClient.get(url, { params, responseType: "blob" }).then(response => { commonApiService.downloadBlob(response); })
 */
const downloadBlob = (response) => {
    let blob = new Blob([response.data], { type: "application/vnd.ms-excel;charset=UTF-8" });
    let filename = response.headers["content-disposition"].split(";")
                    .filter(function(ele) { return ele.indexOf("filename") > -1 })
                    .map(function(ele) { return ele.replace(/"/g, '').split("=")[1] });
    filename = decodeURI(filename);
    if (typeof window.navigator.msSaveBlob !== "undefined") {
      // IE
      window.navigator.msSaveBlob(blob, filename);
    } else {
      let link = document.createElement("a")
      link.href = window.URL.createObjectURL(blob);
      link.download = filename;
      link.click();
    }
  }

export default {
    generateStr,
    downloadBlob
};