export interface User {
    id: string; // MongoDB의 ID는 보통 문자열입니다.
    username: string;
    password: string;
    nickname: string;
    name: string;
    age: number;
    role: string;
    tel: string;
    gender: string;
    enabled: boolean;
    imgId: number | null; // imgId가 없을 수도 있으므로 null로 설정
}
