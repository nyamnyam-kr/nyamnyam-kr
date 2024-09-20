export async function insertReply(reply: ReplyModel): Promise<any | {status: number}> {
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

    if (response.ok && contentType?.includes('application/json')) {
      const data: any = await response.json();
      return data;
    } else {
      const errorMessage = await response.text();
      throw new Error(`Server returned non-JSON response: ${errorMessage}`);
    }
  } catch (error) {
    console.error('Error occurred while inserting reply:', error);
    return {status: 500};
  }
}