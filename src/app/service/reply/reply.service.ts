//src/app/service/reply/reply.service.ts
import { Dispatch } from "@reduxjs/toolkit";
import { deleteReply, fetchReply, insertReply } from "src/app/api/reply/reply.api";
import { initialReply, ReplyModel } from "src/app/model/reply.model";
import { addReplies, getReplies } from "src/lib/features/reply.slice";
import { AppDispatch } from "src/lib/store";

export const toggleReplyService = async (id: number, replyToggles: { [key: number]: boolean }) => {
  const toggled = {
    ...replyToggles,
    [id]: !replyToggles[id],
  };
  if (!replyToggles[id]) {
    const data = await fetchReply(id); // API 호출

    return { toggled, replies: data || [] };
  }
  return { toggled, replies: null };
};

export const submitReplyService = async (postId: number, replyContent: string, currentId: number, replyToggles: { [key: number]: boolean }) => {
  const replyData: ReplyModel = {
    ...initialReply,
    postId: postId,
    content: replyContent,
    userId: currentId
  };
  try {
    const newReply = await insertReply(replyData);

    if (newReply) {
      return toggleReplyService(postId, replyToggles);
    } else {
      return null;
    }
  } catch (error) {
    return { success: false, toggled: replyToggles };
  }
}

export async function insertReplyService(reply: ReplyModel, postId: number, dispatch: AppDispatch): Promise<ReplyModel | null> {
  try {

    const response = await insertReply(reply);

    if (response.ok) {
      const data: ReplyModel = await response.json();

      dispatch(addReplies({ postId, replies: [data] }));

      return data;
    } else {
      const errorMessage = await response.text();
      throw new Error(`서버 응답 에러: ${errorMessage}`);
    }
  } catch (error) {
    console.error('댓글 등록 중 오류 발생:', error);
    return null;
  }
}

export const handleReplyDelete = async (replyId: number, postId: number, dispatch: Dispatch, replies: { [key: number]: ReplyModel[] }) => {
  if (window.confirm("삭제하시겠습니까?")) {
    try {
      await deleteReply(replyId); // 삭제 결과 반환값을 따로 사용하지 않음

      const updateReplies = replies[postId].filter((reply) => reply.id !== replyId);
      dispatch(addReplies({ postId, replies: updateReplies }));

      alert("댓글이 삭제되었습니다.");
    } catch (error: any) {
      console.error("댓글 삭제 중 문제가 발생했습니다:", error);
      alert(error.message); // 에러 메시지 출력
    }
  }
};