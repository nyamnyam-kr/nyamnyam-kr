import { useRouter } from "next/navigation";
import { deletePost } from "src/app/api/post/post.api";
import { AppDispatch } from "src/lib/store";

export const deletePostService = async (
    postId: number,
    restaurantId: number,
    setPosts: React.Dispatch<React.SetStateAction<any[]>>,
    dispatch?: AppDispatch 
  ) => {
    try {
      await deletePost(postId); 
  
      setPosts(prevPosts => prevPosts.filter(post => post.id !== postId));
  
      const router = useRouter();
      router.push(`/post/${restaurantId}`);
  
      alert("게시글이 삭제되었습니다.");
    } catch (error) {
      alert("삭제 중 오류가 발생했습니다.");
      console.error('Error in deletePostService:', error);
    }
  };