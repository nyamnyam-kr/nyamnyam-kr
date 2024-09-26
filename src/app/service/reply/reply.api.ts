import { ReplyModel } from "src/app/model/reply.model";

export async function insertReply(reply: ReplyModel): Promise<ReplyModel | null> {
  try {
    const replyData = {
        id: reply.id, 
        content: reply.content,
        userId: reply.userId,
        postId: reply.postId
    }
    const response = await fetch('http://localhost:8080/api/replies', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(replyData),
    });

    const contentType = response.headers.get('content-type');

    if (response.ok && contentType && contentType.includes('application/json')) {
      const data: ReplyModel = await response.json();
      console.log('댓글 등록 성공 : ',data);
      return data;
    } else {
      const errorMessage = await response.text();
      console.log('서버 응답 에러: ${errorMessage}');
      throw new Error(`서버 응답 에러: ${errorMessage}`);
    }
  } catch (error) {
    console.error('댓글 등록 중 오류 발생:', error);
    return null;
  }
}