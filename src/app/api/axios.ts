import axios from "axios";

export const instance  = axios.create ({
    baseURL : process.env.NEXT_PUBLIC_REACT_APP_SERVER_URL || "http://localhost:8080",
});

export default instance;