"use client";
import React, { FormEvent, useEffect, useState } from "react";
import { useRouter, useParams } from 'next/navigation';
import Star from "../../star/page";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faHeart as solidHeart } from '@fortawesome/free-solid-svg-icons';
import { faHeart as regularHeart } from '@fortawesome/free-regular-svg-icons';
import { getLikeCount, hasLikedPost, likePost, unLikePost } from "../../upvote/page";
import {PostModel} from "src/app/model/post.model";
import {insertReply} from "src/app/service/reply/reply.api";

const reportReasons = [
    "광고글이에요",
    "해당 식당에서 찍은 사진이 아니에요",
    "별점과 후기 내용이 일치하지 않아요",
    "비속어가 포함되어 있어요",
    "다른 사용자에게 불쾌감을 주는 포스트예요",
    "공개하면 안되는 개인정보가 포함되어 있어요",
    "악의적인 포스트를 지속적으로 작성하는 사용자예요",
    "기타 사유"
];

export default function PostList() {
    const [posts, setPosts] = useState<PostModel[]>([]);
    const [postId, setPostId] = useState<number | null>(null);
    const [restaurant, setRestaurant] = useState<RestaurantModel | null>(null);
    const [images, setImages] = useState<{ [key: number]: string[] }>({});
    const [likedPost, setLikedPosts] = useState<number[]>([]);
    const [likeCount, setLikeCounts] = useState<{ [key: number]: number }>({});
    const [replyToggles, setReplyToggles] = useState<{ [key: number]: boolean }>({});
    const [replies, setReplies] = useState<{ [key: number]: ReplyModel[] }>({});
    const [replyInput, setReplyInput] = useState<{ [key: number]: string }>({});
    const [editReply, setEditReply] = useState<{ [key: number]: boolean }>({});
    const [editInput, setEditInput] = useState<{ [key: number]: string }>({});
    const currentUserId = 1; // giveId : 테스트로 1값 설정
    const router = useRouter();
    const { restaurantId } = useParams();
    const [selectedReasons, setSelectedReasons] = useState<{ [key: number]: string }>({});

    useEffect(() => {
        if (restaurantId) {
            fetchPosts();
            fetchRestaurant();
        }
    }, [restaurantId]);

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
            .catch((error) => {
                console.error('There has been a problem with your fetch operation:', error);
            });
    };

    const fetchRestaurant = () => {
        fetch(`http://localhost:8080/api/restaurant/${restaurantId}`)
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Restaurant fetch fail')
                }
                return response.json();
            })
            .then(async (data) => {
                setRestaurant(data);
            })
    };

    const fetchImage = (postId: number) => {
        fetch(`http://localhost:8080/api/images/post/${postId}`)
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Image fetch fail')
                }
                return response.json();
            })
            .then(async (data) => {

                const imageURLs = data.map((image: any) => image.uploadURL);
                setImages(prevImages => ({
                    ...prevImages,
                    [postId]: imageURLs,
                }));
            })
    }

    const fetchReply = (postId: number) => {
        fetch(`http://localhost:8080/api/replies/post/${postId}`)
            .then((response) => response.json())
            .then(data => {
                setReplies(prevReplies => ({
                    ...prevReplies,
                    [postId]: data,
                }));
                console.log("setReplies: ", data);
            })
            .catch((error) => console.error("reply fetch fail:", error));
    }

    useEffect(() => {
        if (postId) {
            fetchReply(postId);
        }
    }, [postId]);

    const handleReplySubmit = async (postId: number, e: FormEvent) => {
        e.preventDefault();

        const replyContent = replyInput[postId];

        if (!replyContent) {
            alert('댓글을 입력하세요.');
            return;
        }

        const replyData = {
            id: 0,
            content: replyContent,
            postId: postId,
            userId: currentUserId
        };

        try {
            const newReply = await insertReply(replyData);

            if (newReply) {
                fetchReply(postId);

                setReplyInput((prevInput) => ({
                    ...prevInput,
                    [postId]: '',
                }));
            } else {
                console.log('댓글 등록 실패');
            }
        } catch (error) {
            console.error('댓글 등록 중 오류:', error);
        }
    };

    const toggleReply = (id: number) => {
        setReplyToggles(prevToggles => ({
            ...prevToggles,
            [id]: !prevToggles[id],
        }));

        if (!replyToggles[id]) {
            fetchReply(id);
        }
    };

    const handleReplyChange = (id: number, content: string) => {
        setReplyInput((prevInput) => ({
            ...prevInput,
            [id]: content,
        }));
        console.log('setReplyInput: ', setReplyInput);
    };

    const formatDate = (dateString: string) => {
        if (!dateString) return '';

        const date = new Date(dateString);
        const options: Intl.DateTimeFormatOptions = { year: '2-digit', month: '2-digit', day: '2-digit' };
        const formattedDate = new Intl.DateTimeFormat('ko-KR', options).format(date);

        const [year, month, day] = formattedDate.split('.').map(part => part.trim());
        return `${year}년 ${month}월 ${day}일`;
    };

    const checkLikedStatus = async (postId: number) => {
        const upvote: UpvoteModel = {
            id: 0,
            giveId: currentUserId,
            haveId: 0,
            postId: postId
        }
        return await hasLikedPost(upvote) ? postId : null;
    };

    const handleLike = async (postId: number) => {
        const upvote: UpvoteModel = {
            id: 0,
            giveId: currentUserId,
            haveId: 0,
            postId: postId
        };

        if (likedPost.includes(postId)) {
            setLikedPosts(prevLikedPosts => prevLikedPosts.filter(id => id !== postId));
            setLikeCounts(prevCounts => ({
                ...prevCounts,
                [postId]: Math.max((prevCounts[postId] || 0) - 1, 0)
            }));

            const success = await unLikePost(upvote);
            if (!success) {
                setLikedPosts(prevLikedPosts => [...prevLikedPosts, postId]);
                setLikeCounts(prevCounts => ({
                    ...prevCounts,
                    [postId]: (prevCounts[postId] || 0) + 1
                }));
            }
        } else {
            setLikedPosts(prevLikedPosts => [...prevLikedPosts, postId]);
            setLikeCounts(prevCounts => ({
                ...prevCounts,
                [postId]: (prevCounts[postId] || 0) + 1
            }));

            const success = await likePost(upvote);
            if (!success) {
                setLikedPosts(prevLikedPosts => prevLikedPosts.filter(id => id !== postId));
                setLikeCounts(prevCounts => ({
                    ...prevCounts,
                    [postId]: Math.max((prevCounts[postId] || 0) - 1, 0)
                }));
            }
        }
    };

    const handleEditClick = (replyId: number, currentContent: string) => {
        setEditReply((prevEdit) => ({
            ...prevEdit,
            [replyId]: true,
        }));
        setEditInput((prevInput) => ({
            ...prevInput,
            [replyId]: currentContent,
        }));
    };

    const handleEditChange = (replyId: number, newContent: string) => {
        setEditInput((prevInput) => ({
            ...prevInput,
            [replyId]: newContent,
        }));
    };

    const handleEditSave = async (replyId: number, postId: number) => {
        const updateReply = {
            id: replyId,
            content: editInput[replyId],
            postId: postId,
            userId: currentUserId
        };

        try {
            const response = await fetch(`http://localhost:8080/api/replies/${replyId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(updateReply)
            });

            if (response.ok) {
                const updateReplyData = await response.json();
                setReplies((prevReplies) => {
                    const updateReplies = { ...prevReplies };
                    if (updateReplies[postId]) {
                        updateReplies[postId] = updateReplies[postId].map((reply) =>
                            reply.id === replyId ? updateReplyData : reply
                        );
                    }
                    fetchReply(postId);
                    return updateReplies;
                });
                setEditReply((prevEditReply) => ({
                    ...prevEditReply,
                    [replyId]: false,
                }));
            } else {
                console.error('댓글 수정 실패 ');
            }
        } catch (error) {
            console.error("댓글 수정 중 에러 발생:", error);
        }
    };

    const handleDelete = (postId: number) => {
        if (window.confirm("게시글을 삭제하시겠습니까?")) {
            fetch(`http://localhost:8080/api/posts/${postId}`, { method: 'DELETE' })
                .then(() => {
                    alert("게시글이 삭제되었습니다.");
                    setPosts(prevPosts => prevPosts.filter(post => post.id !== postId));
                    router.push(`/post/${restaurantId}`);
                })
                .catch(error => {
                    console.error('Delete operation failed:', error);
                    alert("삭제 중 오류가 발생했습니다.");
                });
        }
    };

    const handleReplyDelete = async (replyId: number, postId: number) => {
        if (window.confirm("삭제하시겠습니까?")) {
            try {
                const response = await fetch(`http://localhost:8080/api/replies/${replyId}`, {
                    method: 'DELETE'
                });

                if (response.ok) {
                    setReplies(prevReplies => {
                        return {
                            ...prevReplies,
                            [postId]: prevReplies[postId].filter((reply) => reply.id !== replyId)
                        };
                    });
                    alert("댓글이 삭제되었습니다.");
                    fetchPosts();
                } else {
                    alert("댓글 삭제에 실패했습니다.");
                }
            } catch {
                alert("댓글 삭제 중 문제가 발생했습니다.");
            }
        }
    };

    const postReport = async (postId: number) => {
        const selectedReason = selectedReasons[postId];

        if (!selectedReason) {
            alert("신고 사유를 선택해주세요.");
            return;
        }

        const reportModel = {
            userId: currentUserId,
            postId: postId,
            reason: selectedReason
        };

        try {
            const response = await fetch('http://localhost:8080/api/report/post', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(reportModel),
            });

            if (!response.ok) {
                throw new Error('신고 실패');
            }

            const result = await response.json();
            if (result) {
                alert('신고가 성공적으로 제출되었습니다.');
            } else {
                alert('신고 제출에 실패하였습니다.');
            }
        } catch (error) {
            console.error('신고 중 오류 발생:', error);
            alert('신고 중 오류가 발생했습니다.');
        }
    };

    return (
        <main className="flex min-h-screen flex-col items-center p-6 ">
            <div className="w-full flex justify-end mb-4">
                <button
                    className="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded mr-2"
                    onClick={() => router.push(`/post/register/${restaurantId}`)}>
                    등록하기
                </button>
                <button
                    className="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded mr-2"
                    onClick={() => router.push(`/restaurant/${restaurantId}`)}>
                    뒤로가기
                </button>
            </div>

            {restaurant && (
                <div className="w-full max-w-4xl bg-white shadow-lg rounded-lg p-6 mb-4">
                    <h1 className="text-2xl font-bold">{restaurant.name}</h1>
                </div>
            )}

            <div className="w-full max-w-4xl bg-white shadow-lg rounded-lg p-6">
                <div className="flex flex-col space-y-4">
                    {posts.map((p) => (
                        <div key={p.id} className="flex flex-col md:flex-row border border-indigo-600 rounded-lg p-4 shadow-lg bg-white">
                            <div className="md:w-full">
                                <div>
                                    <div className="flex justify-between items-center mb-2">
                                        <h2 className="text-xl font-semibold mb-2">닉네임: {p.nickname}</h2>

                                        <button
                                            onClick={() => {
                                                handleLike(p.id)
                                            }
                                            }
                                            className="flex items-center text-black rounded-lg py-2 px-4"
                                        >
                                            <FontAwesomeIcon
                                                icon={likeCount[p.id] > 0 ? solidHeart : regularHeart}
                                                style={{ color: likeCount[p.id] > 0 ? 'pink' : 'gray' }}
                                            />
                                            <span className="ml-2">{likeCount[p.id] || 0}</span>
                                        </button>
                                    </div>
                                    <div className="flex space-x-2 mb-2 items-center" style={{whiteSpace: "nowrap"}}>
                                        <Star w="w-4" h="h-4" readonly={true} rate={p.averageRating}/>
                                        <p>{p.averageRating.toFixed(1)} / 5</p>
                                    </div>
                                    <div className="mb-2">
                                        <p className="text-gray-700">{p.content}</p>
                                    </div>
                                    <div className="flex flex-col space-y-2 mb-2">
                                        <div className="inline-flex space-x-2">
                                            <p>맛: </p>
                                            <Star w="w-4" h="h-4" readonly={true} rate={p.taste} />
                                        </div>
                                        <div className="inline-flex space-x-2">
                                            <p>청결: </p>
                                            <Star w="w-4" h="h-4" readonly={true} rate={p.clean} />
                                        </div>
                                        <div className="inline-flex space-x-2">
                                            <p>서비스: </p>
                                            <Star w="w-4" h="h-4" readonly={true} rate={p.service} />
                                        </div>
                                    </div>
                                    <div className="mb-4">
                                        {images[p.id] && images[p.id].length > 0 ? (
                                            <div className="flex flex-wrap gap-4">
                                                {images[p.id].map((url, index) => (
                                                    <img
                                                        key={index}
                                                        src={url}
                                                        alt={`이미지 ${index + 1}`}
                                                        className="w-48 h-auto rounded-lg shadow-lg"
                                                    />
                                                ))}
                                            </div>
                                        ) : (
                                            <p>이미지 없음</p>
                                        )}
                                    </div>
                                    <div className="mb-2 flex items-center">
                                        <h2 className="text-lg font-bold mb-2 flex-shrink-0 self-center">태그:</h2>
                                        {p.tags && p.tags.length > 0 ? (
                                            <ul className="flex flex-wrap gap-2 ml-2 items-center">
                                                {p.tags.map((tag, index) => (
                                                    <li
                                                        key={index}
                                                        className="rounded-full border border-sky-100 bg-sky-50 px-2 py-1 text-sky-700"
                                                    >
                                                        {tag}
                                                    </li>
                                                ))}
                                            </ul>
                                        ) : (
                                            <p className="ml-2">태그 없음</p>
                                        )}
                                    </div>
                                    <div className="flex justify-between items-center mb-2 text-gray-500">
                                        <p>{formatDate(p.entryDate)}</p>
                                        <button
                                            onClick={() => toggleReply(p.id)}
                                            className="bg-transparent hover:bg-gray-200 text-gray-700 font-semibold py-2 px-4 border border-gray-300 rounded">
                                            댓글
                                        </button>
                                    </div>
                                    {replyToggles[p.id] && (
                                        <>
                                            <div className="mt-4 w-full">
                                                {replies[p.id] && replies[p.id].length > 0 ? (
                                                    <ul>
                                                        {replies[p.id].map((reply, index) => (
                                                            <li key={index} className="mb-2 border-b border-gray-200 pb-2 flex items-center justify-between">
                                                                <div className="flex items-center">
                                                                    <span className="inline-block rounded-full bg-gray-300 px-3 py-1 text-sm font-semibold text-gray-700">
                                                                        {reply.nickname}
                                                                    </span>
                                                                    {editReply[reply.id] ? (
                                                                        <span
                                                                            className="ml-2"
                                                                            style={{ width: "600px", display: "inline-block", whiteSpace: "nowrap" }}
                                                                        >
                                                                            <textarea
                                                                                name="content"
                                                                                id="content"
                                                                                value={editInput[reply.id] || reply.content}
                                                                                onChange={(e) => handleEditChange(reply.id, e.target.value)}
                                                                                className="border rounded p-2 w-full"
                                                                                style={{ minHeight: "50px", width: "100%" }}
                                                                            />
                                                                        </span>
                                                                    ) : (
                                                                        <span
                                                                            className="ml-2"
                                                                            style={{ width: "auto", display: "inline-block", whiteSpace: "nowrap" }}
                                                                        >
                                                                            {reply.content}
                                                                        </span>
                                                                    )}
                                                                </div>
                                                                {reply.userId === currentUserId && (
                                                                    <div className="flex space-x-2 mt-2 justify-end">
                                                                        <button
                                                                            className="text-xs bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-1 px-3 border border-blue-500 hover:border-transparent rounded"
                                                                            onClick={() => editReply[reply.id] ? handleEditSave(reply.id, p.id) : handleEditClick(reply.id, reply.content)}
                                                                        >
                                                                            {editReply[reply.id] ? '저장' : '수정'}
                                                                        </button>
                                                                        <button
                                                                            className="text-xs bg-transparent hover:bg-red-500 text-red-700 font-semibold hover:text-white py-1 px-3 border border-red-500 hover:border-transparent rounded"
                                                                            onClick={() => reply.id && handleReplyDelete(reply.id, p.id)}
                                                                        >
                                                                            삭제
                                                                        </button>
                                                                    </div>
                                                                )}
                                                            </li>
                                                        ))}
                                                    </ul>
                                                ) : (
                                                    <p>댓글 없음</p>
                                                )}
                                            </div>
                                            <form onSubmit={(e) => handleReplySubmit(p.id, e)} className="my-4 flex space-x-4">
                                                <input
                                                    type="text"
                                                    placeholder="댓글을 입력하세요."
                                                    value={replyInput[p.id] || ""}
                                                    onChange={(e) => handleReplyChange(p.id, e.target.value)}
                                                    className="border rounded p-2 flex-grow" />
                                                <button
                                                    type="submit"
                                                    className="bg-blue-500 text-white py-2 px-3 rounded hover:bg-blue-600"
                                                >
                                                    등록
                                                </button>
                                            </form>
                                        </>
                                    )}
                                    {p.userId === currentUserId && (
                                        <div className="mt-2">
                                            <button
                                                className="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded mr-2"
                                                onClick={() => {
                                                    router.push(`/post/${restaurantId}/update/${p.id}`);
                                                }}
                                            >
                                                수정
                                            </button>
                                            <button
                                                className="bg-transparent hover:bg-red-500 text-red-700 font-semibold hover:text-white py-2 px-4 border border-red-500 hover:border-transparent rounded mr-2"
                                                onClick={() => {
                                                    handleDelete(p.id);
                                                }}
                                            >
                                                삭제
                                            </button>
                                        </div>
                                    )}
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </main>
    );
}