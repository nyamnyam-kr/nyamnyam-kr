"use client";

import Head from "next/head";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";


export default function Home1() {
  const [chatRooms, setChatRooms] = useState<ChatRoomModel[]>([]);
  const [loading, setLoading] = useState(false);

  const [currentPage, setCurrentPage] = useState(1); // 현재 페이지 상태 추가
  const [totalPages, setTotalPages] = useState(1); // 총 페이지 수 상태 추가

  const [selectChatRooms, setSelectChatRooms] = useState<any[]>([]);
  const router = useRouter();

  // 기본 상태
  useEffect(() => {
    fetchData(currentPage);
  }, [currentPage]); // currentPage가 변경될 때마다 데이터 새로 고침

  const fetchData = async (pageNo: number) => {
    setLoading(true); // 로딩 상태 시작

    try {
      const response = await fetch(`http://localhost:8081/api/chatRoom/findAll`);
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      const data = await response.json();
      setChatRooms(data);

      const totalCountResponse = await fetch('http://localhost:8081/api/chatRoom/count');
      const totalCount = await totalCountResponse.json();
      setTotalPages(Math.ceil(totalCount / 10));
    } catch (error) {
      console.error('Error fetching data:', error);
    } finally {
      setLoading(false); // 로딩 상태 종료
    }
  };


  // 페이지 네이션 적용
  // const fetchChannels = () => {
  //   fetch('http://localhost:8081/api/channels/findAllPerPage/1')
  //     .then((response) => {
  //       if (!response.ok) {
  //         throw new Error('Network response was not ok');
  //       }
  //       return response.json();
  //     })
  //     .then((data) => {
  //       setChannels(data);
  //     })
  //     .catch((error) => {
  //       console.error('There has been a problem with your fetch operation:', error);
  //     });
  // };

  // 디테일 원
  const handleDetails = (id: any) => {
    console.log(id)
    router.push('/channel/details/${id}');
  };

  const handleCheck = (id: any) => {
    setSelectChatRooms((prevSelected: string[]) =>
      prevSelected.includes(id)
        ? prevSelected.filter((channelId: string) => channelId !== id)
        : [...prevSelected, id]
    );
  };

  const handleDelete = () => {
    if (selectChatRooms.length === 0) {
      alert("삭제할 게시글을 선택해주세요.");
      return;
    }
    if (window.confirm("선택한 게시글을 삭제하시겠습니까?")) {
      Promise.all(selectChatRooms.map((id: any) =>
        fetch(`http://localhost:8081/api/chatRoom/deleteById/${id}`, { method: 'DELETE' })
      ))
        .then(() => {
          alert("게시글이 삭제되었습니다.");
          setChatRooms(prevChatRooms =>
            prevChatRooms.filter(room => !selectChatRooms.includes(room.id)) // 삭제한 방을 제외
          );
          setSelectChatRooms([]); // 선택 초기화
        })
        .catch(error => {
          console.error('Delete operation failed:', error);
          alert("삭제 중 오류가 발생했습니다.");
        });
    }
  };


  const handlePage = (pageNo: number) => {
    if (pageNo < 1 || pageNo > totalPages) return; // 페이지 번호 유효성 검사
    setCurrentPage(pageNo);
  };


  const getPageNumbers = () => {
    const pageNumbers = [];
    const maxPagesToShow = 5;
    let startPage = Math.max(currentPage - Math.floor(maxPagesToShow / 2), 1);
    let endPage = Math.min(startPage + maxPagesToShow - 1, totalPages);

    // Adjust the start page if there are not enough pages before the current page
    if (endPage - startPage + 1 < maxPagesToShow) {
      startPage = Math.max(endPage - maxPagesToShow + 1, 1);
    }

    for (let i = startPage; i <= endPage; i++) {
      pageNumbers.push(i);
    }

    return pageNumbers;
  };


  return (
    <>
      <Head>
        <meta charSet="utf-8" />
        <title>TeamHost - Join now and play mighty games!</title>
        <meta name="author" content="Templines" />
        <meta name="description" content="TeamHost" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta name="HandheldFriendly" content="true" />
        <meta name="format-detection" content="telephone=no" />
        <meta httpEquiv="X-UA-Compatible" content="IE=edge" />
        <link rel="shortcut icon" href="/assets/img/favicon.png" type="image/x-icon" />

        {/* CSS Files */}
        <link rel="stylesheet" href="/assets/css/libs.min.css" />
        <link rel="stylesheet" href="/assets/css/main.css" />

        {/* Google Fonts */}
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@100;200;300;400;500;600;700;800;900&display=swap" rel="stylesheet" />
        <link href="https://fonts.googleapis.com/css2?family=Marcellus&display=swap" rel="stylesheet" />
      </Head>

      
    </>
  );
}