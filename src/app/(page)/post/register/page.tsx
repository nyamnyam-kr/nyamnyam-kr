"use client";
import { insertPost } from "@/app/service/post/post.api";
import { useRouter } from "next/navigation";
import { ChangeEvent, FormEvent, useEffect, useState } from "react";
import Star from "../../star/page";

export default function PostRegister() {
  const router = useRouter();
  const [formData, setFormData] = useState<PostModel>({
    id: 0,
    content: '',
    taste: 0,
    clean: 0,
    service: 0,
    entryDate: '', 
    modifyDate: '', 
    averageRating: 0, 
    tags:[]
  });

  const [tagsByCategory, setTagsCategory] = useState<{ [key: string]: TagModel[] }>({});
  const [selectTags, setSelectTags] = useState<string[]>([]);

  // 포스트 등록 페이지에서 항상 모든 태그를 불러옴
  useEffect(() => {
    fetchTagCategory();
  }, []);

  const fetchTagCategory = async () => {
    const response = await fetch('http://localhost:8080/api/tags/category');
    const data = await response.json();
    console.log("불러온 태그 데이터: ", data);  // 데이터를 콘솔에 출력
    setTagsCategory(data); // 모든 태그 데이터를 설정
    setSelectTags([]); // 태그 선택 상태를 초기화
};

  const handleTagSelect = (tag: string) => {
    setSelectTags(prevSelected =>
      prevSelected.includes(tag)
          ? prevSelected.filter(t => t !== tag)
          : [...prevSelected, tag]
    );
  };

  const handleStar = (value: number, field: keyof PostModel) => {
    setFormData((prevData) => ({
      ...prevData,
      [field]: value
    }));
  };

  const handleChange = (
    e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    const { name, value, type, checked } = e.target as HTMLInputElement;
    setFormData({
      ...formData,
      [name]: type === 'checkbox' ? checked : value,
    });
  };

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    const postData = {
      ...formData,
      tags: selectTags
    };
    await insertPost(postData);
    setSelectTags([]); // 태그 선택 초기화
    await fetchTagCategory(); // 모든 태그 목록 재로드
    router.push('/');
  };

  return (
    <main className="flex min-h-screen flex-col items-center p-6">
      <h3 className="font-bold text-xl">[평가하기]</h3>
      <form onSubmit={handleSubmit} className="space-y-4 p-4">
        <div>
          <h2 className="font-bold">[항목별 평점]</h2>
        </div>
        <div className="flex items-center mb-4">
          <label className="block font-bold w-24">맛</label>
          <Star
            w="w-8" h="h-8"
            readonly={false}
            rate={formData.taste}
            onChange={(rating) => handleStar(rating, "taste")}
          />
        </div>
        <div className="flex items-center mb-4">
          <label className="block font-bold w-24">청결</label>
          <Star
            w="w-8" h="h-8"
            readonly={false}
            rate={formData.clean}
            onChange={(rating) => handleStar(rating, "clean")}
          />
        </div>
        <div className="flex items-center mb-4">
          <label className="block font-bold w-24">서비스</label>
          <Star
            w="w-8" h="h-8"
            readonly={false}
            rate={formData.service}
            onChange={(rating) => handleStar(rating, "service")}
          />
        </div>
        <div>
          <h2 className="font-bold">[음식점 키워드]</h2>
          {Object.keys(tagsByCategory).map(category => (
            <div key={category} className="mb-4">
              <h3>
                {category === "방문목적" && "◦ 이 식당은 어떤 방문목적에 적합한가요?"}
                {category === "분위기" && "◦ 이 식당의 분위기를 선택해주세요."}
                {category === "편의시설" && "◦ 이 식당은 어떤 편의시설이 있나요?"}
              </h3>
              <div className="flex flex-wrap gap-4">
                {tagsByCategory[category].map(tag => (
                  <div key={tag.name} className="flex items-center">
                    <input
                      type="checkbox"
                      id={tag.name}
                      name={tag.name}
                      value={tag.name}
                      onChange={() => handleTagSelect(tag.name)}
                    />
                    <label htmlFor={tag.name} className="ml-2">{tag.name}</label>
                  </div>
                ))}
              </div>
            </div>
          ))}
        </div>
        <div>
          <label className="font-bold">[방문후기]</label>
          <input
            type="text"
            name="content"
            value={formData.content}
            onChange={handleChange}
            className="border rounded p-2 w-full"
          />
        </div>
        <button type="submit" className="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500">
          등록하기
        </button>
      </form>
    </main>
  );
}
