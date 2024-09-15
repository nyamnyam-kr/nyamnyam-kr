"use client";
import Star from '@/app/(page)/star/page';
import { useParams, useRouter } from 'next/navigation';
import { FormEvent, useEffect, useState } from 'react'

export default function PostUpdate() {
  const router = useRouter();
  const { id } = useParams();
  const [allTags, setAllTags] = useState<{[category: string]: TagModel[]}>({});
  const [selectTags, setSelectTags] = useState<string[]>([]);

  const [formData, setFormData] = useState<PostModel>({
    id: 0,
    taste: 0,
    clean: 0,
    service: 0,
    content: '',
    entryDate: '',
    modifyDate: '',
    averageRating: 0,
    tags: []
  });

  useEffect(() => {
    const fetchPost = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/posts/${id}`);
        if (!response.ok) {
          throw new Error('Failed to fetch post');
        }
        const data: PostModel = await response.json();
        setFormData(data);
        setSelectTags(data.tags);
      } catch (error) {
        console.error('Error fetching post:', error);
      }
    };

    const fetchTags = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/tags/category');
        if (!response.ok) {
          throw new Error('Failed to fetch tags');
        }
        const data = await response.json();
        const arrayTags: TagModel[] = [];
        for (const categoryTags of Object.values(data) as TagModel[][]) {
          arrayTags.push(...categoryTags);
        }
        setAllTags(data);
      } catch (error) {
        console.error('Error fetching tags:', error);
      }
    };


    if (id) {
      fetchPost();
    }
    fetchTags();
  }, [id]);

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    try {
      const updatePost = {
        ...formData,
        id: formData.id,   
        tags: selectTags,
      };
      console.log("서버로 보낼 데이터:", updatePost);

      const response = await fetch(`http://localhost:8080/api/posts/${id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(updatePost)
      });

      if (!response.ok) {
        throw new Error('Failed to update post');
      }

      router.push(`/post/details/${id}`);
    } catch (error) {
      console.error('Error updating post:', error);
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleTagSelect = (tag: string) => {
    setSelectTags((prevSelected) =>
      prevSelected.includes(tag)
        ? prevSelected.filter((t) => t !== tag)
        : [...prevSelected, tag]
    );
  };

  const handleStar = (value: number, field: keyof PostModel) => {
    setFormData((prevData) => ({
      ...prevData,
      [field]: value
    }));
  };

  return (
    <main className="flex min-h-screen flex-col items-center">
      <h1 className="text-2xl font-bold mb-6">[게시글 수정]</h1>
      <form onSubmit={handleSubmit} className="w-full max-w-2xl">
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
          {Object.keys(allTags).map(category => (
            <div key={category} className="mb-4">
              <h3>
                {category === "방문목적" && "◦ 이 식당은 어떤 방문목적에 적합한가요?"}
                {category === "분위기" && "◦ 이 식당의 분위기를 선택해주세요."}
                {category === "편의시설" && "◦ 이 식당은 어떤 편의시설이 있나요?"}
              </h3>
              <div className="flex flex-wrap gap-4">
                {(allTags[category] as TagModel[]).map(t => (
                  <div key={t.name} className="flex items-center">
                    <input
                      type="checkbox"
                      id={t.name}
                      name={t.name}
                      checked={selectTags.includes(t.name)}
                      onChange={() => handleTagSelect(t.name)}
                    />
                    <label htmlFor={t.name} className='ml-2'>{t.name}</label>
                  </div>
                ))}
              </div>
            </div>
          ))}
        </div>
        <div>
          <label className="font-bold">[방문후기]</label>
          <textarea
            id="content"
            name="content"
            value={formData.content}
            onChange={handleChange}
            className="w-full p-2 border rounded"
            rows={3}
          />
        </div>
        <button type="submit" className="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600">
          수정
        </button>
      </form>
    </main>
  );
}