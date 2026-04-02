interface IconProps {
  name: string;
  size?: number;
}

const paths: Record<string, (s: number) => React.ReactElement> = {
  dashboard: (s) => (
    <svg width={s} height={s} viewBox="0 0 16 16" fill="none">
      <rect x="1" y="1" width="6" height="6" rx="1" stroke="currentColor" strokeWidth="1.2"/>
      <rect x="9" y="1" width="6" height="6" rx="1" stroke="currentColor" strokeWidth="1.2"/>
      <rect x="1" y="9" width="6" height="6" rx="1" stroke="currentColor" strokeWidth="1.2"/>
      <rect x="9" y="9" width="6" height="6" rx="1" stroke="currentColor" strokeWidth="1.2"/>
    </svg>
  ),
  incarico: (s) => (
    <svg width={s} height={s} viewBox="0 0 16 16" fill="none">
      <rect x="2" y="1" width="12" height="14" rx="1.5" stroke="currentColor" strokeWidth="1.2"/>
      <path d="M5 5h6M5 8h6M5 11h4" stroke="currentColor" strokeWidth="1.2" strokeLinecap="round"/>
    </svg>
  ),
  bordero: (s) => (
    <svg width={s} height={s} viewBox="0 0 16 16" fill="none">
      <path d="M8 1L14 4v8l-6 3L2 12V4l6-3z" stroke="currentColor" strokeWidth="1.2"/>
      <path d="M8 1v14M2 4l6 3 6-3" stroke="currentColor" strokeWidth="1.2"/>
    </svg>
  ),
  clienti: (s) => (
    <svg width={s} height={s} viewBox="0 0 16 16" fill="none">
      <circle cx="8" cy="5" r="3" stroke="currentColor" strokeWidth="1.2"/>
      <path d="M2 14c0-3.314 2.686-6 6-6s6 2.686 6 6" stroke="currentColor" strokeWidth="1.2" strokeLinecap="round"/>
    </svg>
  ),
  veicoli: (s) => (
    <svg width={s} height={s} viewBox="0 0 16 16" fill="none">
      <path d="M1 9l2-5h10l2 5" stroke="currentColor" strokeWidth="1.2" strokeLinejoin="round"/>
      <rect x="1" y="9" width="14" height="4" rx="1" stroke="currentColor" strokeWidth="1.2"/>
      <circle cx="4" cy="14" r="1.5" stroke="currentColor" strokeWidth="1.2"/>
      <circle cx="12" cy="14" r="1.5" stroke="currentColor" strokeWidth="1.2"/>
    </svg>
  ),
  chevron: (s) => (
    <svg width={s} height={s} viewBox="0 0 16 16" fill="none">
      <path d="M4 6l4 4 4-4" stroke="currentColor" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round"/>
    </svg>
  ),
  plus: (s) => (
    <svg width={s} height={s} viewBox="0 0 16 16" fill="none">
      <path d="M8 3v10M3 8h10" stroke="currentColor" strokeWidth="1.5" strokeLinecap="round"/>
    </svg>
  ),
  trash: (s) => (
    <svg width={s} height={s} viewBox="0 0 16 16" fill="none">
      <path d="M3 4h10M6 4V2h4v2M5 4l.5 9h5l.5-9" stroke="currentColor" strokeWidth="1.2" strokeLinecap="round" strokeLinejoin="round"/>
    </svg>
  ),
  search: (s) => (
    <svg width={s} height={s} viewBox="0 0 16 16" fill="none">
      <circle cx="7" cy="7" r="4.5" stroke="currentColor" strokeWidth="1.2"/>
      <path d="M10.5 10.5l3 3" stroke="currentColor" strokeWidth="1.2" strokeLinecap="round"/>
    </svg>
  ),
  check: (s) => (
    <svg width={s} height={s} viewBox="0 0 16 16" fill="none">
      <path d="M3 8l4 4 6-6" stroke="currentColor" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round"/>
    </svg>
  ),
  alert: (s) => (
    <svg width={s} height={s} viewBox="0 0 16 16" fill="none">
      <path d="M8 1l7 13H1L8 1z" stroke="currentColor" strokeWidth="1.2" strokeLinejoin="round"/>
      <path d="M8 6v4M8 11.5v.5" stroke="currentColor" strokeWidth="1.2" strokeLinecap="round"/>
    </svg>
  ),
  pkg: (s) => (
    <svg width={s} height={s} viewBox="0 0 16 16" fill="none">
      <path d="M8 1L14 4v8l-6 3L2 12V4l6-3z" stroke="currentColor" strokeWidth="1.2"/>
      <path d="M8 1v6m0 0L2 4m6 3l6-3" stroke="currentColor" strokeWidth="1.2"/>
    </svg>
  ),
};

export default function Icon({ name, size = 16 }: IconProps) {
  const fn = paths[name];
  return fn ? fn(size) : null;
}
