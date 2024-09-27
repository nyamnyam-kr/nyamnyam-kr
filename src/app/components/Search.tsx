// Search.tsx
import React from 'react';
import { useSearchContext } from './SearchContext';

const Search: React.FC = () => {
  const { setSearchTerm } = useSearchContext();

  return (
    <div className="search">
      <div className="search__input">
        <i className="ico_search"></i>
        <input
          type="search"
          name="search"
          placeholder="Search"
          onChange={(e) => setSearchTerm(e.target.value)} // 입력 시 Context의 searchTerm 업데이트
        />
      </div>
      <div className="search__btn">
        <button type="button">
          <i className="ico_microphone"></i>
        </button>
      </div>
    </div>
  );
};

export default Search;