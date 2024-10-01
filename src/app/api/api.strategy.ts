import instance from "./axios"

const getStrategy = async (url: string, params?: any) => {
    return await instance.get(url, { params });
};

const postStrategy = async (url: string, data: any) => {
    return await instance.post(url, data);
};

const postWithParamsStrategy = async (url: string, params?: any, data: any = {}) => {
    return await instance.post(url, data, { params });
};

const putStrategy = async (url: string, data: any) => {
    return await instance.put(url, data);
};

const deleteStrategy = async (url: string) => {
    return await instance.delete(url);
};

export const strategy = {
    GET: getStrategy,
    POST: postStrategy,
    POST_PARAMS: postWithParamsStrategy,
    PUT: putStrategy,
    DELETE: deleteStrategy,
};