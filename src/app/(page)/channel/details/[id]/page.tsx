"use client"; // 이 라인을 추가하세요

import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { useParams } from "next/navigation";

export default function ChannelDetail() {
  const router = useRouter();
  const params = useParams();
  const [channelData, setChannelData] = useState<any>(null);

  useEffect(() => {
    // 채널 ID로 데이터를 가져오는 비동기 함수 호출
    const fetchData = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/channels/${params.id}`);
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const data = await response.json();
        setChannelData(data);
      } catch (error) {
        console.error('Error fetching channel data:', error);
      }
    };

    fetchData();
  }, [params.id]);

  return (
    <div>
      <h1>Channel Details</h1>
      {channelData ? (
        <div>
          <p>Channel Name: {channelData.name}</p>
          <p>
            Participants:{" "}
            {channelData.participants && channelData.participants.length > 0
              ? channelData.participants.join(", ")
              : "No participants available"}
          </p>
        </div>
      ) : (
        <p>Loading...</p>
      )}
    </div>
  );
}
