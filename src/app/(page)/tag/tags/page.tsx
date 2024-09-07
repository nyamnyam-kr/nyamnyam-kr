"use client"

import Link from "next/link";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";

export default function TagList() {
  const [tags, setTags] = useState<TagModel[]>([]);
  const [selectTags, setSelectTags] = useState<number[]>([]);
  const router = useRouter();

  useEffect(()=> {
    fetchTag();
  },[]);
  
  const fetchTag = () => {
    fetch('http://localhost:8080/tags/group')
        .then((response) => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then((data) => {
          setTags(data);
        })
        .catch((error) => {
            console.error('There has been a problem with your fetch operation:', error);
        });
};

const handleCheck = (id:number) => {
    setSelectTags(prevSelected => 
      prevSelected.includes(id)
      ? prevSelected.filter(tagId => tagId !== id)
      : [...prevSelected, id]
    )
}

const handleDetails = (id: number) => {
    router.push('tags/details/${id}');
};

const handleDelete = () => {
    if (selectTags.length == 0) {
      alert("삭제할 태그를 선택해주세요.")
      return;
    }
    if(window.confirm("선택한 태그를 삭제하시겠습니까?")){
      Promise.all(selectTags.map(id =>
        fetch(`http://localhost:8080/tags/${id}`,
          {method: 'DELETE'})
      ))
        .then(()=> {
          alert("태그가 삭제되었습니다.")
          setSelectTags([]);
          fetchTag();
        })
        .catch(error => {
           alert("삭제 중 오류가 발생했습니다.");
        });
    }
};

  return (
    <main className="flex min-h-screen flex-col items-center">
      <div className="w-full max-w-4xl bg-white shadow-lg rounded-lg p-6">
          <table className="w-full border-collapse bg-white shadow-md rounded-lg overflow-hidden">
            <thead>
                <tr className="bg-blue-600 text-white">
                    <th className="py-3 px-4 border-b"></th>
                    <th className="py-3 px-4 border-b">No</th>
                    <th className="py-3 px-4 border-b">Tag</th>
                </tr>
            </thead>
            <tbody>
             {tags.map((t)=> (
              <tr key={t.id} className="border border-indigo-600">
                  <td className="py-3 px-4 border-b">
                    <input 
                        type="checkbox"
                        checked={selectTags.includes(t.id)}
                        onChange={()=>handleCheck(t.id)} 
                    />
                  </td>
                  <td className="py-3 px-4 border-b">
                    <Link
                          href={`/tag/details/${t.id}`}
                          className="text-blue-600 hover:underline"
                          onClick={(e)=> {
                            e.stopPropagation();
                            handleDetails(t.id ?? 0);
                          }}
                    >
                    {t.id}
                    </Link>
                    </td>
                  <td className="py-3 px-4 border-b">{t.name}</td>
              </tr>
             ))}
            </tbody>
          </table>
          <div className="mt-4">
            <button className="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded mr-2"
                    onClick={()=> router.push('/tag/register')}>
              등록하기
            </button>
            <button className="bg-transparent hover:bg-red-500 text-red-700 font-semibold hover:text-white py-2 px-4 border border-red-500 hover:border-transparent rounded"
              onClick={handleDelete}>
                삭제하기
            </button>
          </div>
      </div>
    </main>
  );
}