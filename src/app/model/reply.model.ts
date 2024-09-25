interface ReplyModel{
    id:number;
    content:string;
    postId: number;
    userId: number;
    nickname?: string;
    entryDate?: string;
    modifyDate?: string;
}