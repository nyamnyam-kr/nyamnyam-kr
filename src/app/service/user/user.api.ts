// src/app/service/user.service.ts
import { User } from "src/app/model/user.model";
import {
    fetchUserExists,
    fetchUserById,
    fetchAllUsers,
    fetchUserCount,
    deleteUserById,
    updateUser,
    registerUser,
    loginUser,
} from "src/app/api/user/user.api";

// 사용자 존재 여부 확인 서비스
export const checkUserExists = async (id: string): Promise<boolean> => {
    return await fetchUserExists(id);
};

// 사용자 정보 가져오기 서비스
export const getUserById = async (id: string): Promise<User> => {
    return await fetchUserById(id);
};

// 모든 사용자 가져오기 서비스
export const getAllUsers = async (): Promise<User[]> => {
    return await fetchAllUsers();
};

// 사용자 수 가져오기 서비스
export const getUserCount = async (): Promise<number> => {
    return await fetchUserCount();
};

// 사용자 삭제 서비스
export const removeUserById = async (id: string): Promise<void> => {
    await deleteUserById(id);
};

// 사용자 정보 업데이트 서비스
export const modifyUser = async (user: User): Promise<User> => {
    return await updateUser(user);
};

// 사용자 등록 서비스
export const addUser = async (user: User): Promise<User> => {
    return await registerUser(user);
};

// 사용자 로그인 서비스
export const authenticateUser = async (username: string, password: string): Promise<string> => {
    return await loginUser(username, password);
};
