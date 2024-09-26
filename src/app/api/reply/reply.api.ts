// src/app/api/reply/reply.api.ts

export const insertReply = async (replyData: any): Promise<Response> => {
  return await fetch('http://localhost:8080/api/replies', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(replyData),
  } 
);
  
  
};

export const deleteReply = async (replyId: number) => {
  const response = await fetch(`http://localhost:8080/api/replies/${replyId}`, {
    method: 'DELETE',
  });

  if (!response.ok) {
    throw new Error('댓글 삭제에 실패했습니다.');
  }

  return response;
};



