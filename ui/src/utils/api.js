import request from "@/utils/request";

export function searchFile(search) {
  return request({
    url: "/file/search",
    method: "post",
    data: search,
  });
}

export function downloadFile(fileId) {
  return request({
    url: "/file/downloadFile/" + fileId,
    method: "get",
    responseType: 'blob'
  });
}

export function deleteFile(fileId) {
  return request({
    url: "/file/" + fileId,
    method: "delete"
  });
}