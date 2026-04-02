import { useForm, useFieldArray, Controller, useWatch, FormProvider } from 'react-hook-form';
import type { MerceItem, IncaricoPaylod } from '../../lib/types';
import AccordionSection from '../ui/AccordionSection';
import ClientSelector from '../ui/ClientSelector';
import MerceItemComponent from '../ui/MerceItem';
import Icon from '../ui/Icon';
import { toast } from '../ui/Toast';
import { API_BASE } from '../../lib/constants';
import { useState } from 'react';
import type { FormValues } from '../../lib/types';

/*interface FormValues {
  ldv: string;
  ddt: string;
  data_consegna_prevista: string;
  mitt: string | object | null;
  dest: string | object | null;
  vett_mitt: string | object | null;
  vett_dest: string | object | null;
  ritiro: boolean;
  valore_doganale: string;
  valore_assicurazione: string;
  contrassegno: boolean;
  contrassegno_valore: string;
  contrassegno_tipo: string;
  note: string;
  merce: MerceItem[];
}*/

const newMerce = (): MerceItem => ({
  segnacollo: '', merce_tipo: '', peso_kg: '', volume_m3: '',
  fragile: false, epal: false, id_epal: '', _hasAdr: false, adr: undefined,
});

const defaultValues: FormValues = {
  ldv: '', ddt: '', data_consegna_prevista: '',
  mitt: null, dest: null, vett_mitt: null, vett_dest: null,
  ritiro: false, valore_doganale: '', valore_assicurazione: '',
  contrassegno: false, contrassegno_valore: '', contrassegno_tipo: '',
  note: '', merce: [newMerce()],
};


export default function FormIncarico() {
  const [open, setOpen] = useState({
    main: true, parti: true, merce: true, opt: false, note: false,
  });
  const [loading, setLoading] = useState(false);
  

  const tog = (k: keyof typeof open) =>
    setOpen((p) => ({ ...p, [k]: !p[k] }));

  const methods = useForm<FormValues>({ defaultValues });

  const {
    register,
    control,
    handleSubmit,
    reset,
    watch,
    formState: { errors },
  } = methods;

  const { fields, append, remove, update } = useFieldArray({
    control,
    name: 'merce',
  });

  // watch per campi condizionali
  const contrassegno = watch('contrassegno');
  const merceValues = watch('merce');


  const buildPayload = (data: FormValues): IncaricoPaylod => {
    const p: IncaricoPaylod = {
      ldv: data.ldv,
      data_consegna_prevista: data.data_consegna_prevista,
      ritiro: data.ritiro,
      mitt: data.mitt as string,
      dest: data.dest as string,
      merce: data.merce.map((m) => {
        const o: Omit<MerceItem, '_hasAdr'> = {
          segnacollo: m.segnacollo,
          merce_tipo: Number(m.merce_tipo),
        };
        if (m.peso_kg) o.peso_kg = m.peso_kg;
        if (m.volume_m3) o.volume_m3 = m.volume_m3;
        o.fragile = !!m.fragile;
        if (m.epal) { o.epal = true; if (m.id_epal) o.id_epal = m.id_epal; }
        if (m._hasAdr && m.adr)
          o.adr = {
            ...m.adr,
            qta: String(Number(m.adr.qta)),
            id_adr_elemento: Number(m.adr.id_adr_elemento),
          };
        return o;
      }),
    };
    if (data.ddt) p.ddt = data.ddt;
    if (data.vett_mitt) p.vett_mitt = data.vett_mitt as string;
    if (data.vett_dest) p.vett_dest = data.vett_dest as string;
    if (data.valore_doganale) p.valore_doganale = Number(data.valore_doganale);
    if (data.valore_assicurazione) p.valore_assicurazione = Number(data.valore_assicurazione);
    if (data.contrassegno) {
      p.contrassegno = true;
      p.contrassegno_valore = Number(data.contrassegno_valore);
      p.contrassegno_tipo = Number(data.contrassegno_tipo);
    }
    if (data.note.trim()) p.note = data.note.trim();
    return p;
  };


  const onSubmit = async (data: FormValues) => {
    const payload = buildPayload(data);
    setLoading(true);
    try {
      await fetch(`${API_BASE}/incarico`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload),
      });
      toast('Incarico creato con successo');
      reset(defaultValues);
    } catch {
      toast("Errore durante l'invio", 'error');
    } finally {
      setLoading(false);
    }
  };


  return (
    <FormProvider {...methods}>
      <form onSubmit={handleSubmit(onSubmit)} noValidate className="max-w-3xl">

        {/* 1 — Dati principali */}
        <AccordionSection
          num="1" title="Dati principali" subtitle="LDV, date, tipo incarico"
          open={open.main} onToggle={() => tog('main')}
        >
          <div className="grid grid-cols-2 gap-3 mb-3.5">
            <CF label="LDV" required err={errors.ldv?.message}>
              <input
                className={`cf-inp ${errors.ldv ? 'border-red-500' : ''}`}
                placeholder="es. 123456789"
                {...register('ldv', { required: 'LDV obbligatorio' })}
              />
            </CF>
            <CF label="DDT" opt>
              <input className="cf-inp" type="date" {...register('ddt')} />
            </CF>
          </div>

          <div className="grid grid-cols-2 gap-3 mb-3.5">
            <CF label="Data consegna prevista" required err={errors.data_consegna_prevista?.message}>
              <input
                className={`cf-inp ${errors.data_consegna_prevista ? 'border-red-500' : ''}`}
                type="date"
                {...register('data_consegna_prevista', { required: 'Data obbligatoria' })}
              />
            </CF>
          </div>

          <Chk id="ritiro" label="Ritiro" {...register('ritiro')} />
        </AccordionSection>

        {/* 2 — Parti coinvolte */}
        <AccordionSection
          num="2" title="Parti coinvolte" subtitle="Mittente, destinatario, vettori"
          open={open.parti} onToggle={() => tog('parti')}
        >
          <div className="grid grid-cols-1 md:grid-cols-2 gap-5 mb-5">
            <div>
              <Controller
                name="mitt"
                control={control}
                rules={{ required: 'Mittente obbligatorio' }}
                render={({ field }) => (
                  <ClientSelector label="Mittente" required value={field.value} onChange={field.onChange} />
                )}
              />
              {errors.mitt && <p className="text-red-400 text-xs mt-1">{errors.mitt.message}</p>}
            </div>
            <div>
              <Controller
                name="dest"
                control={control}
                rules={{ required: 'Destinatario obbligatorio' }}
                render={({ field }) => (
                  <ClientSelector label="Destinatario" required value={field.value} onChange={field.onChange} />
                )}
              />
              {errors.dest && <p className="text-red-400 text-xs mt-1">{errors.dest.message}</p>}
            </div>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 gap-5">
            <Controller
              name="vett_mitt"
              control={control}
              render={({ field }) => (
                <ClientSelector label="Vettore mittente" value={field.value} onChange={field.onChange} />
              )}
            />
            <Controller
              name="vett_dest"
              control={control}
              render={({ field }) => (
                <ClientSelector label="Vettore destinatario" value={field.value} onChange={field.onChange} />
              )}
            />
          </div>
        </AccordionSection>

        {/* 3 — Merce */}
        <AccordionSection
          num="3" title="Merce" subtitle={`${fields.length} collo/i`}
          open={open.merce} onToggle={() => tog('merce')}
        >
          <div className="space-y-2.5 mb-3.5">
            {fields.map((field, i) => (
              <MerceItemComponent
                key={field.id}
                idx={i}
                item={merceValues[i]}
                onChange={(v) => update(i, v)}
                onDelete={() => remove(i)}
              />
            ))}
          </div>
          <button
            type="button"
            onClick={() => append(newMerce())}
            className="flex items-center gap-2 px-4 py-2 rounded-lg bg-[#F97316]/10 border border-[#F97316]/25 text-[#F97316] text-sm hover:bg-[#F97316]/20 transition-colors"
          >
            <Icon name="plus" size={14} />Aggiungi collo
          </button>
        </AccordionSection>

        {/* 4 — Valori e contrassegno */}
        <AccordionSection
          num="4" title="Valori e contrassegno" subtitle="Doganale, assicurazione, contrassegno"
          open={open.opt} onToggle={() => tog('opt')}
        >
          <div className="grid grid-cols-2 gap-3 mb-3.5">
            <CF label="Valore doganale (€)" opt>
              <input className="cf-inp" type="number" step="0.01" {...register('valore_doganale')} />
            </CF>
            <CF label="Valore assicurazione (€)" opt>
              <input className="cf-inp" type="number" step="0.01" {...register('valore_assicurazione')} />
            </CF>
          </div>

          <Chk id="contrassegno" label="Incarico in contrassegno" {...register('contrassegno')} />

          {contrassegno && (
            <div className="grid grid-cols-2 gap-3 mt-3.5">
              <CF label="Valore contrassegno (€)" required err={errors.contrassegno_valore?.message}>
                <input
                  className={`cf-inp ${errors.contrassegno_valore ? 'border-red-500' : ''}`}
                  type="number"
                  step="0.01"
                  {...register('contrassegno_valore', {
                    required: contrassegno ? 'Valore obbligatorio' : false,
                  })}
                />
              </CF>
              <CF label="Tipo contrassegno" required err={errors.contrassegno_tipo?.message}>
                <select
                  className={`cf-inp ${errors.contrassegno_tipo ? 'border-red-500' : ''}`}
                  {...register('contrassegno_tipo', {
                    required: contrassegno ? 'Tipo obbligatorio' : false,
                  })}
                >
                  <option value="">Seleziona…</option>
                  <option value="1">Contante</option>
                  <option value="2">Assegno</option>
                  <option value="3">Bonifico</option>
                </select>
              </CF>
            </div>
          )}
        </AccordionSection>

        {/* 5 — Note */}
        <AccordionSection
          num="5" title="Note" subtitle="Informazioni aggiuntive"
          open={open.note} onToggle={() => tog('note')}
        >
          <CF label="Note" opt>
            <textarea
              className="cf-inp resize-y min-h-[80px]"
              placeholder="Note aggiuntive per l'incarico…"
              {...register('note')}
            />
          </CF>
        </AccordionSection>

        {/* Actions */}
        <div className="flex gap-3 pt-2 mt-1 border-t border-[#0F3D5C]">
          <button
            type="submit"
            disabled={loading}
            className="flex items-center gap-2 px-5 py-2.5 rounded-lg bg-[#F97316] hover:bg-[#EA6C0A] text-white text-sm font-medium transition-all disabled:opacity-40 shadow-lg hover:shadow-[0_0_20px_rgba(249,115,22,.3)]"
          >
            {loading ? 'Invio in corso…' : <><Icon name="check" size={14} />Crea incarico</>}
          </button>
          <button
            type="button"
            onClick={() => reset(defaultValues)}
            className="px-5 py-2.5 rounded-lg border border-[#1A5C8A] text-[#94A3B8] text-sm hover:border-[#F97316] hover:text-white transition-colors"
          >
            Reset
          </button>
        </div>
      </form>
    </FormProvider>
  );
}

// ─── Helper components ────────────────────────────────────────────────────────

function CF({
  label, required, opt, err, children,
}: {
  label: string; required?: boolean; opt?: boolean; err?: string; children: React.ReactNode;
}) {
  return (
    <div className="flex flex-col gap-1.5">
      <label className="font-mono text-[11px] uppercase tracking-wider text-[#7FA8C4]">
        {label}
        {required && <span className="text-[#F97316] ml-0.5">*</span>}
        {opt && <span className="opacity-50 ml-1 normal-case">(opz.)</span>}
      </label>
      {children}
      {err && <span className="text-red-400 text-[11px]">{err}</span>}
    </div>
  );
}

import { forwardRef } from 'react';

const Chk = forwardRef<
  HTMLInputElement,
  { id: string; label: string } & React.InputHTMLAttributes<HTMLInputElement>
>(({ id, label, ...rest }, ref) => (
  <label htmlFor={id} className="flex items-center gap-2 text-sm text-[#94A3B8] cursor-pointer py-1">
    <input id={id} type="checkbox" className="accent-[#F97316] w-4 h-4" ref={ref} {...rest} />
    {label}
  </label>
));
Chk.displayName = 'Chk';