export async function insertPost(post: PostModel): Promise<any | {status: number}> {
  try {
    const body = {
        id: post.id, 
        content: post.content,
        taste: post.taste,
        clean: post.clean,
        service: post.service,
        entryDate: post.entryDate,
        modifyDate: post.modifyDate
    }
    const response = await fetch('http://localhost:8080/api/posts', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(body),
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
    console.error('Error occurred while inserting post:', error);
    return {status: 500};
  }
}