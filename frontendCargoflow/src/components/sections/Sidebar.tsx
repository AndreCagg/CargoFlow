import type { PageId } from '../../lib/constants';
import { NAV_MODULES } from '../../lib/constants';
import Icon from '../ui/Icon';

interface SidebarProps {
  page: PageId;
  onNavigate: (p: PageId) => void;
}

const groups = [...new Set(NAV_MODULES.filter((m) => m.group).map((m) => m.group))];

export default function Sidebar({ page, onNavigate }: SidebarProps) {
  return (
    <nav className="w-[220px] min-w-[220px] bg-[#07213A] border-r border-[#0F3D5C] flex flex-col overflow-hidden max-md:w-14 max-md:min-w-[56px]">
      {/* logo */}
      <div className="px-4 py-5 border-b border-[#0F3D5C] flex items-center gap-2.5">
        <div className="w-8 h-8 rounded bg-[#F97316] flex items-center justify-center shrink-0">
          <svg width="16" height="16" viewBox="0 0 16 16" fill="none">
            <rect x="1" y="6" width="14" height="8" rx="1.5" stroke="white" strokeWidth="1.3"/>
            <path d="M5 6V4.5a3 3 0 016 0V6" stroke="white" strokeWidth="1.3" strokeLinecap="round"/>
            <line x1="8" y1="9" x2="8" y2="11" stroke="white" strokeWidth="1.3" strokeLinecap="round"/>
          </svg>
        </div>
        <span className="font-mono font-medium text-sm text-[#F1F5F9] tracking-tight max-md:hidden">CargoFlow</span>
      </div>

      {/* nav */}
      <div className="flex-1 py-3 overflow-y-auto">
        {NAV_MODULES.filter((m) => !m.group).map((m) => (
          <NavItem key={m.id} id={m.id} label={m.label} active={page === m.id} onClick={() => onNavigate(m.id as PageId)} />
        ))}
        {groups.map((g) => (
          <div key={g}>
            <p className="px-4 pt-3 pb-1.5 font-mono text-[9px] uppercase tracking-[.15em] text-[#7FA8C4] opacity-60 max-md:hidden">{g}</p>
            {NAV_MODULES.filter((m) => m.group === g).map((m) => (
              <NavItem key={m.id} id={m.id} label={m.label} active={page === m.id} onClick={() => onNavigate(m.id as PageId)} />
            ))}
          </div>
        ))}
      </div>
    </nav>
  );
}

function NavItem({ id, label, active, onClick }: { id: string; label: string; active: boolean; onClick: () => void }) {
  return (
    <div
      onClick={onClick}
      className={`flex items-center gap-2.5 px-4 py-2.5 cursor-pointer text-sm transition-all border-l-2 max-md:justify-center max-md:px-3 ${
        active
          ? 'bg-[#F97316]/8 text-[#F97316] border-l-[#F97316]'
          : 'text-[#94A3B8] border-l-transparent hover:bg-white/[0.03] hover:text-[#F1F5F9]'
      }`}
    >
      <span className={active ? 'opacity-100' : 'opacity-70'}>
        <Icon name={id} size={15} />
      </span>
      <span className="max-md:hidden">{label}</span>
    </div>
  );
}
