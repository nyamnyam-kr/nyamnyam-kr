/** @type {import('next').NextConfig} */
const nextConfig = {
  swcMinify: true, // SWC 활성화
  experimental: {
    esmExternals: false, // ESM 외부 모듈 처리 비활성화 시도
  },
  images: {
    domains: ['search.pstatic.net'], // 여기에 사용하려는 이미지 도메인 추가
  },

  webpack: (config, { isServer }) => {
    config.cache = false; // 캐시 비활성화
    return config;
  },
  
};



export default nextConfig;
