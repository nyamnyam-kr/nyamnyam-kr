interface ReplyModel{
    id?:number;
    content:string;
    postId: number;
    userId: number;
    entryDate?: string;
    modifyDate?: string;
}