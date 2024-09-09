import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";

const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: "냠냠!",
  description: "Created with Next.js",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <head>
        <title>냠냠!</title>
      </head>
      <body className={inter.className}>
        <header>
          <nav className="navbar">
            <a href="/">홈</a>
            <a href="#about">소개</a>
            <a href="#services">서비스</a>
            <a href="#contact">연락처</a>
          </nav>
        </header>
        <main>{children}</main>
        <footer className="footer">
          <p>&copy; 2024 냠냠! 모든 권리 보유.</p>
        </footer>
      </body>
    </html>
  );
}
