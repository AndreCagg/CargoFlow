import { useState, useEffect } from 'react';
import Icon from './Icon';

interface ToastItem {
  id: number;
  msg: string;
  type: 'success' | 'error';
}

type ToastFn = (msg: string, type?: 'success' | 'error') => void;

let _toast: ToastFn = () => {};

export function toast(msg: string, type: 'success' | 'error' = 'success') {
  _toast(msg, type);
}

export default function Toast() {
  const [toasts, setToasts] = useState<ToastItem[]>([]);

  useEffect(() => {
    _toast = (msg, type = 'success') => {
      const id = Date.now();
      setToasts((p) => [...p, { id, msg, type }]);
      setTimeout(() => setToasts((p) => p.filter((t) => t.id !== id)), 3500);
    };
  }, []);

  return (
    <div className="fixed bottom-6 right-6 flex flex-col gap-2 z-50">
      {toasts.map((t) => (
        <div
          key={t.id}
          className={`flex items-center gap-2.5 px-4 py-3 rounded-lg border text-sm animate-slide-in ${
            t.type === 'success'
              ? 'bg-green-500/10 border-green-500/30 text-green-400'
              : 'bg-red-500/10 border-red-500/30 text-red-400'
          }`}
        >
          <Icon name={t.type === 'success' ? 'check' : 'alert'} size={14} />
          {t.msg}
        </div>
      ))}
    </div>
  );
}
