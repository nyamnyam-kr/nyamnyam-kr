"use client";
import React, { FormEvent, useEffect, useState } from "react";
import { useRouter, useParams } from "next/navigation";
import Star from "../../../components/Star";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faHeart as solidHeart } from '@fortawesome/free-solid-svg-icons';
import { faHeart as regularHeart } from '@fortawesome/free-regular-svg-icons';
import { PostModel } from "src/app/model/post.model";
import { ReplyModel } from "src/app/model/reply.model";
import { replyService } from "src/app/service/reply/reply.service";
import { upvoteService} from "src/app/service/upvote/upvote.service";
import { imageService } from "src/app/service/image/image.service";
import { postService } from "src/app/service/post/post.service";
import { fetchRestaurantService } from "src/app/service/restaurant/restaurant.service";
import {fetchReportRegister} from "src/app/service/report/report.service";
import {fetchNoticeRegister} from "src/app/service/notice/notice.service";
import {ReportModel} from "src/app/model/report.model";

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
    const [restaurant, setRestaurant] = useState<RestaurantModel | null>(null);
    const [images, setImages] = useState<{ [key: number]: string[] }>({});
    const [likedPost, setLikedPosts] = useState<number[]>([]);
    const [likeCount, setLikeCounts] = useState<{ [key: number]: number }>({});
    const [replyToggles, setReplyToggles] = useState<{ [key: number]: boolean }>({});
    const [replies, setReplies] = useState<{ [key: number]: ReplyModel[] }>({});
    const [replyInput, setReplyInput] = useState<{ [key: number]: string }>({});
    const [editReply, setEditReply] = useState<{ [key: number]: boolean }>({});
    const [editInput, setEditInput] = useState<{ [key: number]: string }>({});
    const currentUserId = 1; // 확인용
    const router = useRouter();
    const { restaurantId } = useParams();
    const [selectedReasons, setSelectedReasons] = useState<{ [key: number]: string }>({});
    const [reportingPostId, setReportingPostId] = useState<number | null>(null);
    const [reportReason, setReportReason] = useState<string>("");


    useEffect(() => {
        if (restaurantId) {
            fetchPosts(Number(restaurantId));
            fetchRestaurant();
        }
    }, [restaurantId]);

    const fetchPosts = async (restaurantId: number) => {
        try {
            const postData = await postService.fetchPost(restaurantId);

            setPosts(postData.map((data) => data.post));
            setLikedPosts(postData.filter((data) => data.liked).map((data) => data.post.id));

            setLikeCounts(
                postData.reduce((acc, data) => {
                    acc[data.post.id] = data.count;
                    return acc;
                }, {} as { [key: number]: number })
            );

            const updatedImages: { [key: number]: string[] } = {};
            for (const data of postData) {
                const imageURLs = await imageService.getByPostId(data.post.id);
                updatedImages[data.post.id] = imageURLs;
            }
            setImages(updatedImages);

        } catch (error) {
            console.error("loadPosts error:", error);
        }
    };

    const fetchRestaurant = async () => {
        if (restaurantId) {
            const data = await fetchRestaurantService(Number(restaurantId));
            if (data) setRestaurant(data);
        }
    };

    const fetchImage = async (postId: number) => {
        const imageURLs = await imageService.getByPostId(postId)

        setImages(prevImages => ({
            ...prevImages,
            [postId]: imageURLs,
        }));
    }

    const handleDelete = async (postId: number) => {
        if (window.confirm("게시글을 삭제하시겠습니까?")) {
            const success = await postService.remove(postId);

            if (success) {
                alert("게시글이 삭제되었습니다.");
                setPosts(prevPosts => prevPosts.filter(post => post.id !== postId));
                router.push(`/post/${restaurantId}`);
            }
        }
    };

    // 댓글 버튼 
    const toggleReply = async (id: number) => {
        const { toggled, replies } = await replyService.toggle(id, replyToggles);

        setReplyToggles((prevToggles) => ({
            ...prevToggles,
            [id]: toggled[id],
        }));

        setReplies(prevReplies => ({
            ...prevReplies,
            [id]: replies || prevReplies[id],
        }));
    }

    // 댓글 작성 (서버 연결)
    const replySubmit = async (postId: number, e: FormEvent) => {
        e.preventDefault();

        const replyContent = replyInput[postId];
        if (!replyContent) {
            alert('댓글을 입력하세요.');
            return;
        }
        const result = await replyService.submit(postId, replyContent, currentUserId, replyToggles);

        if (result && result.success) {
            const { newReply } = result;

            setReplies((prevReplies) => ({
                ...prevReplies,
                [postId]: [...(prevReplies[postId] || []), newReply],
            }));

            setReplyInput((prevInput) => ({
                ...prevInput,
                [postId]: '',
            }));
        }
    };

    // 댓글 작성 & 수정 
    const replyInputChange = (id: number, content: string, isEdit: boolean) => {
        if (isEdit) { // 댓글 작성 (postId)
            setReplyInput((prevInput) => ({
                ...prevInput,
                [id]: content,
            }));
        } else { // 댓글 수정 (replyId)
            setEditInput((prevInput) => ({
                ...prevInput,
                [id]: content,
            }));
        }
    }

    // 수정 & 저장 버튼 
    const replyEditClick = (replyId: number, currentContent: string) => {
        setEditReply((prevEdit) => ({
            ...prevEdit,
            [replyId]: true,
        }));
        setEditInput((prevInput) => ({
            ...prevInput,
            [replyId]: currentContent,
        }));
    };

    // 수정내용 저장 (서버연결)
    const replyEditSave = async (replyId: number, postId: number) => {
        const updateReply = await replyService.editSave(replyId, postId, editInput[replyId], currentUserId);
        if (updateReply) {
            setReplies((prevReplies) => ({
                ...prevReplies,
                [postId]: prevReplies[postId]?.map((reply) =>
                    reply.id === replyId ? updateReply : reply
                ),
            }));

            setEditReply((prevEditReply) => ({
                ...prevEditReply,
                [replyId]: false,
            }));
        } else {
            console.log("댓글 수정 실패");
        }
    };

    // 댓글 삭제 
    const replyDelete = async (replyId: number, postId: number) => {
        if (!window.confirm("댓글을 삭제하시겠습니까?")) return;

        const updatedReplies = await replyService.remove(replyId, postId, replies);

        if (updatedReplies) {
            setReplies(prevReplies => ({
                ...prevReplies,
                [postId]: updatedReplies
            }));
        }
    };

    // 날짜 포맷 지정 
    const formatDate = (dateString: string) => {
        if (!dateString) return '';

        const date = new Date(dateString);
        const options: Intl.DateTimeFormatOptions = { year: '2-digit', month: '2-digit', day: '2-digit' };
        const formattedDate = new Intl.DateTimeFormat('ko-KR', options).format(date);

        const [year, month, day] = formattedDate.split('.').map(part => part.trim());
        return `${year}년 ${month}월 ${day}일`;
    };

    // 좋아요 상태 확인 
    const checkLikedStatus = async (postId: number) => {
        const isLiked = await upvoteService.check(postId, currentUserId);
        return isLiked ? postId : null;
    };

    // 좋아요 & 취소 & count
    const handleLike = async (postId: number) => {
        const result = await upvoteService.toggle(postId, currentUserId, likedPost);

        if (result) {
            setLikedPosts(result.likedPost);
            setLikeCounts((prevCounts) => ({
                ...prevCounts,
                [postId]: (prevCounts[postId] || 0) + result.likeCountDelta,
            }))
        }
    };

    const postReport = async (postId: number) => {
        const selectedReason = reportReason;

        if (!selectedReason) {
            alert("신고 사유를 선택해주세요.");
            return;
        }

        const reportModel: ReportModel = {
            userId: currentUserId,
            postId: postId,
            reason: selectedReason
        };


        try {
            await fetchReportRegister(reportModel);
            alert('신고가 성공적으로 제출되었습니다.');

        } catch (error) {
            console.error('신고 중 오류 발생:', error);
            alert('신고 중 오류가 발생했습니다.');
        }
    };

    const handleReportClick = (postId: number) => {
        setReportingPostId(postId);
        setReportReason("");
    };

    const handleReportSubmit = async (e: FormEvent) => {
        e.preventDefault();
        if (reportingPostId !== null) {
            await postReport(reportingPostId);
            setReportingPostId(null);
        }
    };

    return (
        <main className="flex min-h-screen flex-col items-center p-6 " style={{ marginTop: '30px' }}>
            {restaurant && (
                <div className="w-full max-w-4xl bg-white shadow-lg rounded-lg p-3 mb-2 items-center h-16">
                    <h1 className="text-2xl font-bold">{restaurant.name}</h1>
                </div>
            )}

            <div className="w-full max-w-4xl flex justify-end mt-4">
                <button
                    className="bg-transparent hover:bg-gray-200 text-gray-700 font-semibold py-2 px-4 border border-gray-300 rounded mr-2"
                    onClick={() => router.push(`/post/register/${restaurantId}`)}>
                    등록하기
                </button>
                <button
                    className="bg-transparent hover:bg-gray-200 text-gray-700 font-semibold py-2 px-4 border border-gray-300 rounded mr-2"
                    onClick={() => router.push(`/restaurant/${restaurantId}`)}>
                    뒤로가기
                </button><button
                    className="bg-transparent hover:bg-gray-200 text-gray-700 font-semibold py-2 px-4 border border-gray-300 rounded"
                    onClick={() => router.push(`/post/${restaurantId}/default`)}>
                    CSS
                </button>
            </div>

            <div className="w-full max-w-4xl bg-white shadow-lg rounded-lg p-6">
                <div className="flex flex-col space-y-4">
                    {posts.map((p) => (
                        <div key={p.id} className="flex flex-col md:flex-row border border-[#F46119] rounded-lg p-4 shadow-lg bg-white">
                            <div className="md:w-full">
                                <div>
                                    <div className="flex justify-between items-center mb-2">
                                        <h2 className="text-xl font-semibold mb-2">닉네임: {p.nickname}</h2>

                                        <button
                                            onClick={() => {
                                                handleLike(p.id)
                                            }}
                                            className="flex items-center text-black rounded-lg py-2 px-4"
                                        >
                                            <FontAwesomeIcon
                                                icon={likeCount[p.id] > 0 ? solidHeart : regularHeart}
                                                style={{ color: likeCount[p.id] > 0 ? 'pink' : 'gray' }}
                                            />
                                            <span className="ml-2">{likeCount[p.id] || 0}</span>
                                        </button>
                                    </div>
                                    <div className="flex space-x-2 mb-2 items-center" style={{ whiteSpace: "nowrap" }}>
                                        <Star w="w-4" h="h-4" readonly={true} rate={p.averageRating} />
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
                                                    className="rounded-full border border-[#F46119] px-3 py-1 text-[#F46119] font-semibold"
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
                                        <p>{formatDate(
                                            p.entryDate)}</p>
                                        <button
                                            onClick={() => toggleReply(p.id)}
                                            className="bg-transparent hover:bg-gray-200 text-gray-700 font-semibold py-2 px-4 border border-gray-300 rounded">
                                            댓글
                                        </button>
                                        <button
                                            onClick={() => handleReportClick(p.id)}
                                            className="bg-transparent hover:bg-red-500 text-red-700 font-semibold hover:text-white py-2 px-4 border border-red-500 hover:border-transparent rounded">
                                            신고
                                        </button>
                                    </div>
                                    {reportingPostId === p.id && (
                                        <div className="mt-4">
                                            <form onSubmit={handleReportSubmit} className="flex flex-col">
                                                <label className="text-gray-700 mb-2">신고 사유를 선택하세요:</label>
                                                <select
                                                    value={reportReason}
                                                    onChange={(e) => setReportReason(e.target.value)}
                                                    className="border rounded p-2 mb-4"
                                                >
                                                    <option value="">선택하세요</option>
                                                    {reportReasons.map((reason, index) => (
                                                        <option key={index} value={reason}>{reason}</option>
                                                    ))}
                                                </select>
                                                <button
                                                    type="submit"
                                                    className="bg-blue-500 text-white py-2 px-3 rounded hover:bg-blue-600"
                                                >
                                                    신고하기
                                                </button>
                                                <button
                                                    type="button"
                                                    onClick={() => setReportingPostId(null)}
                                                    className="mt-2 bg-transparent hover:bg-gray-300 text-gray-700 font-semibold py-2 px-4 border border-gray-300 rounded"
                                                >
                                                    취소
                                                </button>
                                            </form>
                                        </div>
                                    )}
                                    {replyToggles[p.id] && (
                                        <>
                                            <div className="mt-4 w-full">
                                                {replies[p.id] && replies[p.id].length > 0 ? (
                                                    <ul>
                                                        {replies[p.id].map((reply, index) => (
                                                            <li key={index} className="mb-2 border-b border-gray-200 pb-2">
                                                                <div className="flex items-center justify-between">
                                                                    <div className="flex items-center">
                                                                        <span className="inline-block rounded-full bg-gray-300 px-3 py-1 text-sm font-semibold text-gray-700">
                                                                            {reply.nickname}
                                                                        </span>
                                                                        {editReply[reply.id] ? (
                                                                            <span className="ml-2" style={{ width: "600px", display: "inline-block", whiteSpace: "nowrap" }}>
                                                                                <textarea
                                                                                    name="content"
                                                                                    id="content"
                                                                                    value={editInput[reply.id] || reply.content}
                                                                                    onChange={(e) => replyInputChange(reply.id, e.target.value, false)}
                                                                                    className="border rounded p-2 w-full"
                                                                                    style={{ minHeight: "50px", width: "100%" }}
                                                                                />
                                                                            </span>
                                                                        ) : (
                                                                            <span className="ml-2" style={{ width: "auto", display: "inline-block", whiteSpace: "nowrap" }}>
                                                                                {reply.content}
                                                                            </span>
                                                                        )}
                                                                    </div>
                                                                    <div className="flex items-center">
                                                                        <span className="text-gray-500 mr-4">{formatDate(reply.entryDate)}</span>
                                                                        {reply.userId === currentUserId && (
                                                                            <div className="flex space-x-2">
                                                                                <button
                                                                                    className="text-xs bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-1 px-3 border border-blue-500 hover:border-transparent rounded"
                                                                                    onClick={() => editReply[reply.id] ? replyEditSave(reply.id, p.id) : replyEditClick(reply.id, reply.content)}
                                                                                >
                                                                                    {editReply[reply.id] ? '저장' : '수정'}
                                                                                </button>
                                                                                <button
                                                                                    className="text-xs bg-transparent hover:bg-red-500 text-red-700 font-semibold hover:text-white py-1 px-3 border border-red-500 hover:border-transparent rounded"
                                                                                    onClick={() => reply.id && replyDelete(reply.id, p.id)}
                                                                                >
                                                                                    삭제
                                                                                </button>
                                                                            </div>
                                                                        )}
                                                                    </div>
                                                                </div>
                                                            </li>
                                                        ))}
                                                    </ul>
                                                ) : (
                                                    <p>댓글 없음</p>
                                                )}
                                            </div>
                                            <form onSubmit={(e) => replySubmit(p.id, e)} className="my-4 flex space-x-4">
                                                <input
                                                    type="text"
                                                    placeholder="댓글을 입력하세요."
                                                    value={replyInput[p.id] || ""}
                                                    onChange={(e) => replyInputChange(p.id, e.target.value, true)}
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
                                        <div className="flex justify-end mt-2">
                                            <button
                                                className="bg-transparent hover:bg-gray-200 text-gray-700 font-semibold py-2 px-4 border border-gray-300 rounded mr-2"
                                                onClick={() => {
                                                    router.push(`/post/${restaurantId}/update/${p.id}`);
                                                }}
                                            >
                                                수정
                                            </button>
                                            <button
                                                className="bg-transparent hover:bg-gray-200 text-gray-700 font-semibold py-2 px-4 border border-gray-300 rounded mr-2"
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
};