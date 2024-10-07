export interface UpvoteModel {
    id: number;
    giveId: number;
    haveId: number;
    postId: number;
}

export const initialUpvote: UpvoteModel = {
    id: 0,
    giveId: 0,
    haveId: 0,
    postId: 0
}