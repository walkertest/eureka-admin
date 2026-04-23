import Cookies from 'js-cookie';

export function setCookie(name: string, value: string, days: number = 30): void {
  const expires = new Date();
  expires.setTime(expires.getTime() + days * 24 * 60 * 60 * 1000);

  Cookies.set(name, value, {
    expires,
    path: '/',
  });
}

export function getCookie(name: string): string | undefined {
  return Cookies.get(name);
}

export function removeCookie(name: string): void {
  Cookies.remove(name, { path: '/' });
}
