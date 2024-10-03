/** @type {import('next').NextConfig} */
const nextConfig = {
  swcMinify: true, // SWC 활성화
  experimental: {
    esmExternals: false, // ESM 외부 모듈 처리 비활성화 시도
  },
  webpack: (config, { isServer }) => {
    config.cache = false; // 캐시 비활성화
    return config;
  },
  images: {
    domains: ['kr.object.ncloudstorage.com'], // 외부 도메인 허용
  },
};



export default nextConfig;
