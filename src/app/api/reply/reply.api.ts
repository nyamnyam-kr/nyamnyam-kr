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

  return response;
};



