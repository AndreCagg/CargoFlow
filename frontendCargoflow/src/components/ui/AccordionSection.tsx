import { ReactNode } from 'react';
import Icon from './Icon';

interface AccordionSectionProps {
  num: string;
  title: string;
  subtitle?: string;
  open: boolean;
  onToggle: () => void;
  children: ReactNode;
}

export default function AccordionSection({
  num, title, subtitle, open, onToggle, children,
}: AccordionSectionProps) {
  return (
    <div className="bg-[#112E4A] border border-[#0F3D5C] rounded-xl mb-3.5 overflow-hidden">
      <div
        onClick={onToggle}
        className="flex items-center justify-between px-5 py-4 cursor-pointer hover:bg-white/[0.02] transition-colors"
      >
        <div className="flex items-center gap-3">
          <span className="w-6 h-6 rounded bg-[#F97316]/15 border border-[#F97316]/30 flex items-center justify-center font-mono text-[11px] text-[#F97316] shrink-0">
            {num}
          </span>
          <div>
            <div className="text-sm font-medium text-[#F1F5F9]">{title}</div>
            {subtitle && <div className="text-xs text-[#7FA8C4] mt-0.5">{subtitle}</div>}
          </div>
        </div>
        <span className={`text-[#7FA8C4] transition-transform duration-250 ${open ? 'rotate-180' : ''}`}>
          <Icon name="chevron" size={16} />
        </span>
      </div>
      {open && (
        <div className="px-5 pb-5 pt-4 border-t border-[#0F3D5C]">
          {children}
        </div>
      )}
    </div>
  );
}
