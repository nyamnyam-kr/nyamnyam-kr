//src/app/(page)/post/register/[restaurantId]

"use client";
import { useParams, useRouter } from "next/navigation";
import { ChangeEvent, FormEvent, useEffect, useState } from "react";
import Star from "../../../../components/Star";
import { PostModel } from "src/app/model/post.model";
import { postService } from "src/app/service/post/post.service";
import { tag } from "src/app/api/tag/tag.api";
import { TagModel } from "src/app/model/tag.model";


export default function PostRegister() {
  const router = useRouter();
  const { restaurantId } = useParams();
  const [formData, setFormData] = useState<PostModel>({} as PostModel);

  const [tagsByCategory, setTagsCategory] = useState<{ [key: string]: TagModel[] }>({});
  const [tags, setTags] = useState<string[]>([]);
  const [images, setImages] = useState<File[]>([]);

  useEffect(() => {
    setFormData((prevData) => ({
      ...prevData,
      restaurantId: Number(restaurantId)
    }));
    fetchTagCategory();
  }, [restaurantId]);

  const fetchTagCategory = async () => {
    try {
      const result = await tag.getByCategories();
      setTagsCategory(result);
      setTags([]);
    } catch (error) {
      console.error("태그 목록을 불러오는데 실패했습니다.", error);
    }
  };

  const handleTagSelect = (tag: string) => {
    setTags(prevSelected =>
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

  const uploadImage = (e: ChangeEvent<HTMLInputElement>) => {
    if (e.target.files) {
      setImages(Array.from(e.target.files));
    }
  };

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();

    try {
      const postId: number = await postService.insert({
        content: formData.content,
        taste: formData.taste,
        clean: formData.clean,
        service: formData.service,
        tags: tags,
        restaurantId: formData.restaurantId
      }, images);

      if (postId) {
        setTags([]);
        router.push(`/post/${restaurantId}/details/${postId}`);
      }
    } catch (error) {
      console.error('Post submission failed:', error);
    }
  };

  return (
    <main className="flex min-h-screen flex-col items-center p-6" style={{ marginTop: '30px' }}>
      <h3 className="font-bold text-xl">[평가하기]</h3>
      <form onSubmit={handleSubmit} className="space-y-4 p-4" encType="multipart/form-data">
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
              <div className="flex flex-wrap gap-2">
                {tagsByCategory[category].map(tag => (
                  <div key={tag.name} className="flex items-center mr-1 mb-1">
                    <input
                      type="checkbox"
                      id={tag.name}
                      name={tag.name}
                      value={tag.name}
                      onChange={() => handleTagSelect(tag.name)}
                      className="absolute opacity-0 peer"
                    />
                    <label
                      htmlFor={tag.name}
                      className="peer-checked:bg-[#F46119] peer-checked:text-white !peer-checked:text-white flex items-center justify-center cursor-pointer rounded-full border border-[#F46119] bg-white text-[#F46119] px-3 py-1 transition duration-200 ease-in-out"
                    >
                      {tag.name}
                    </label>
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
        <div>
          <label className="font-bold">[이미지 첨부]</label>
          <input
            type="file"
            multiple accept="image/*"
            onChange={uploadImage}
            className="border rounded p-2 w-full"
          />
        </div>
        <div className="flex mt-4">
          <button type="submit" className="bg-transparent hover:bg-gray-200 text-gray-700 font-semibold py-2 px-4 border border-gray-300 rounded mr-2">
            등록하기
          </button>
          <button
            type="button"
            className="bg-transparent hover:bg-gray-200 text-gray-700 font-semibold py-2 px-4 border border-gray-300 rounded mr-2"
            onClick={() => router.push(`/post/${restaurantId}`)}>
            뒤로가기
          </button>
        </div>
      </form>
    </main>

  );
}

