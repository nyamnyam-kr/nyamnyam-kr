export interface ReplyModel{
    id:number;
    content:string;
    postId: number;
    userId: number;
    nickname: string;
    entryDate: string;
    modifyDate?: string;
}

export const initialReply: ReplyModel = {
    id: 0,
    content: '',
    postId: 0,
    userId: 0,
    nickname: '',
    entryDate: '',
    modifyDate: '',
}