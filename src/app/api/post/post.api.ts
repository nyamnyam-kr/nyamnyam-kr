import { PostModel } from "src/app/model/post.model";

const fetchPosts = () => {
  fetch(`http://localhost:8080/api/posts/${restaurantId}/group`)
      .then((response) => {
          if (!response.ok) {
              throw new Error('Network response was not ok');
          }
          return response.json();
      })
      .then(async (data) => {
          setPosts(data);

          setLikedPosts(likedPostId);
          setLikeCounts(likeCountMap);
      })
      .catch((error) => {
          console.error('There has been a problem with your fetch operation:', error);
      });
};


export async function insertPost(post: PostModel): Promise<any | {status: number}> {
  try {
    const body = {
        id: post.id, 
        content: post.content,
        taste: post.taste,
        clean: post.clean,
        service: post.service,
        entryDate: post.entryDate,
        modifyDate: post.modifyDate,
        tags:post.tags,
        restaurantId:post.restaurantId
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


// likeCount, likeChecked, getimg 한번에 가져오기 