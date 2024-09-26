const likeStatus = data.map(async (post: PostModel) => {
    const liked = await checkLikedStatus(post.id);
    const count = await getLikeCount(post.id);
    await fetchImage(post.id);
    return { postId: post.id, liked, count };
});

const result = await Promise.all(likeStatus);

const likedPostId = result
    .filter(result => result.liked)
    .map(result => result.postId);

const likeCountMap = result.reduce((acc, result) => {
    acc[result.postId] = result.count;
    return acc;
}, {} as { [key: number]: number });

setLikedPosts(likedPostId);
setLikeCounts(likeCountMap);
})