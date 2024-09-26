export const noticeAll = async () => {
    const response = await fetch('http://localhost:8080/api/notice', {
        method: 'GET'
    });
    if (!response.ok) {
        throw new Error("Failed to fetch group details");
    }
    return await response.json(); // JSON 데이터를 반환
};



export const showNotice = async (id : number) => {
    const response = await fetch(`http://localhost:8080/api/notice/${id}`, {
        method: 'GET'});
    if (!response.ok) {
        throw new Error("Failed to fetch notice details");
    }

    return response;
}