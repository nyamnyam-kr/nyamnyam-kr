// src/app/api/user.api.ts

import { User } from "src/app/model/user.model";

// 사용자 존재 여부 확인
export const fetchUserExists = async (id: string): Promise<boolean> => {
    const response = await fetch(`http://localhost:8081/api/user/existsById?id=${id}`);
    if (!response.ok) {
        throw new Error('Failed to fetch user existence');
    }
    return response.json();
};

// 사용자 정보 가져오기
export const fetchUserById = async (id: string): Promise<User> => {
    const response = await fetch(`http://localhost:8081/api/user/findById?id=${id}`);
    if (!response.ok) {
        throw new Error('Failed to fetch user by ID');
    }
    return response.json();
};

// 모든 사용자 가져오기
export const fetchAllUsers = async (): Promise<User[]> => {
    const response = await fetch(`http://localhost:8081/api/user/findAll`);
    if (!response.ok) {
        throw new Error('Failed to fetch all users');
    }
    return response.json();
};

// 사용자 수 가져오기
export const fetchUserCount = async (): Promise<number> => {
    const response = await fetch(`http://localhost:8081/api/user/count`);
    if (!response.ok) {
        throw new Error('Failed to fetch user count');
    }
    return response.json();
};

// 사용자 삭제
export const deleteUserById = async (id: string): Promise<void> => {
    const response = await fetch(`http://localhost:8081/api/user/deleteById?id=${id}`, {
        method: 'DELETE',
    });
    if (!response.ok) {
        throw new Error('Failed to delete user');
    }
};

// 사용자 정보 업데이트
export const updateUser = async (user: User): Promise<User> => {
    const response = await fetch(`http://localhost:8081/api/user/update`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(user),
    });
    if (!response.ok) {
        throw new Error('Failed to update user');
    }
    return response.json();
};

// 사용자 등록
export const registerUser = async (user: User): Promise<User> => {
    const response = await fetch(`http://localhost:8081/api/user/join`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(user),
    });
    if (!response.ok) {
        throw new Error('Failed to register user');
    }
    return response.json();
};

// 사용자 로그인
export const loginUser = async (username: string, password: string): Promise<string> => {
    const response = await fetch(`http://localhost:8081/api/user/login?username=${username}&password=${password}`, {
        method: 'POST',
    });
    if (!response.ok) {
        throw new Error('Failed to log in');
    }
    return response.json();
};
